package engine.phase;

import controller.GameWindowController;
import engine.MethodRepository;
import engine.RequirementsValidator;
import engine.action.*;
import model.cards.AdventureCard;
import model.character.Character;
import model.character.MainCharacter;
import model.data.GlobalData;
import model.elements.Marker;
import model.elements.tiles.IslandTile;
import model.enums.ActionType;
import model.enums.cards.IdeaType;
import model.enums.elements.ResourceType;
import model.enums.elements.TokenType;
import model.enums.elements.dices.DiceWallType;
import model.enums.elements.dices.DicesGroupType;
import model.resources.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ActionPhase implements Phase {
	private static boolean firstPlayerCanUseOnlyOneMarker = false;
	private static boolean noActionCanBePerformedByTwoDifferentMarkers = false;
	private static Map<ActionType, Set<TokenType>> actionsTokens = new HashMap<>();
	private Logger logger = LogManager.getLogger(ActionPhase.class);
	private List<Action> allActions = new ArrayList<>();
	private List<Action> actionsToPerform = new ArrayList<>();
	private List<Marker> allMarkers = new ArrayList<>();
	private Resources tmpResources = new Resources();
	private boolean needOneMoreWoodForBuildingParamsAction = false;

	ActionPhase() {
		actionsTokens.put(ActionType.THREAT_ACTION, new HashSet<>());
		actionsTokens.put(ActionType.HUNTING_ACTION, new HashSet<>());
		actionsTokens.put(ActionType.BUILDING_ACTION, new HashSet<>());
		actionsTokens.put(ActionType.RESOURCES_ACTION, new HashSet<>());
		actionsTokens.put(ActionType.EXPLORATION_ACTION, new HashSet<>());
		actionsTokens.put(ActionType.CAMP_ACTION, new HashSet<>());
		actionsTokens.put(ActionType.REST_ACTION, new HashSet<>());
	}

	public static boolean isFirstPlayerCanUseOnlyOneMarker() {
		return firstPlayerCanUseOnlyOneMarker;
	}

	public static void setFirstPlayerCanUseOnlyOneMarker(boolean firstPlayerCanUseOnlyOneMarker) {
		ActionPhase.firstPlayerCanUseOnlyOneMarker = firstPlayerCanUseOnlyOneMarker;
	}

	public static boolean isNoActionCanBePerformedByTwoDifferentMarkers() {
		return noActionCanBePerformedByTwoDifferentMarkers;
	}

	public static void setNoActionCanBePerformedByTwoDifferentMarkers(boolean noActionCanBePerformedByTwoDifferentMarkers) {
		ActionPhase.noActionCanBePerformedByTwoDifferentMarkers = noActionCanBePerformedByTwoDifferentMarkers;
	}

	public static Map<ActionType, Set<TokenType>> getActionsTokens() {
		return actionsTokens;
	}

	public static void setActionsTokens(Map<ActionType, Set<TokenType>> actionsTokens) {
		ActionPhase.actionsTokens = actionsTokens;
	}

	public boolean isNeedOneMoreWoodForBuildingParamsAction() {
		return needOneMoreWoodForBuildingParamsAction;
	}

	public void setNeedOneMoreWoodForBuildingParamsAction(boolean needOneMoreWoodForBuildingParamsAction) {
		this.needOneMoreWoodForBuildingParamsAction = needOneMoreWoodForBuildingParamsAction;
	}

	@Override
	public void initializePhase() {
		logger.info("---> Phase: ACTION");

		// Clearing all lists
		allActions.clear();
		actionsToPerform.clear();
		allMarkers.clear();
		tmpResources.setFoodAmount(GlobalData.getAvailableResources().getFoodAmount());
		tmpResources.setLongTermFoodAmount(GlobalData.getAvailableResources().getLongTermFoodAmount());
		tmpResources.setWoodAmount(GlobalData.getAvailableResources().getWoodAmount());
		tmpResources.setFurAmount(GlobalData.getAvailableResources().getFurAmount());

		// Preparing markers
		GlobalData.getCharacters().forEach(character -> {
			if (character == GlobalData.getFirstPlayer() && firstPlayerCanUseOnlyOneMarker) {
				allMarkers.add(character.getMarkers().get(0));
				firstPlayerCanUseOnlyOneMarker = false;
			} else {
				allMarkers.addAll(character.getMarkers());
			}
		});
		allMarkers.addAll(GlobalData.getAvailableActionHelpers());

		// Preparing all actions
		allActions.addAll(prepareAllThreatActions());
		allActions.addAll(prepareAllHuntingActions());
		allActions.addAll(prepareAllBuildingActions());
		allActions.addAll(prepareAllResourcesActions());
		allActions.addAll(prepareAllExplorationActions());
		allActions.add(new CampOrderingAction(ActionType.CAMP_ACTION));
		allActions.add(new RestingAction(ActionType.REST_ACTION));

		// Handle actions choosing
		boolean ready = false; // TODO: "Ready" button
		while (!ready) {
			updateActionsAvailability();
			Action chosenAction = GameWindowController.chooseAction(allActions);
			if (chosenAction instanceof BuildingAction /*todo && akcja wymaga drewna*/) {
				if (actionsTokens.get(ActionType.BUILDING_ACTION).contains(TokenType.ONE_MORE_WOOD_TOKEN)) {
					actionsTokens.get(ActionType.BUILDING_ACTION).remove(TokenType.ONE_MORE_WOOD_TOKEN);
				}
				BuildingAction buildingAction = (BuildingAction) chosenAction;
				if (buildingAction.getIdeaType() == IdeaType.PARAM_SHELTER) {
					IslandTile islandTile = GameWindowController.chooseIslandTile(GlobalData.getDiscoveredIslandTiles());
					buildingAction.setPlaceForShelter(islandTile);
				}
			}
			chosenAction.setMinAssignedMarkersNumber(calculateMinMarkersNumber(chosenAction));
			List<Marker> chosenMarkers = GameWindowController.chooseMarkers(allActions, chosenAction, allMarkers, noActionCanBePerformedByTwoDifferentMarkers);
			prepareAction(chosenAction, chosenMarkers, GlobalData.getMainCharactersNumber());
			updateTmpResources(chosenAction);
			ready = true;
		}

		// Sorting actions order
		actionsToPerform.sort(Comparator.comparingInt(Action::getOrderOfExecution));
	}

	private List<Action> prepareAllThreatActions() {
		List<Action> threatActions = new ArrayList<>();
		GlobalData.getThreatActionCards().forEach(eventCard -> threatActions.add(new ThreatAction(ActionType.THREAT_ACTION, eventCard)));
		return threatActions;
	}

	private List<Action> prepareAllHuntingActions() {
		List<Action> huntingActions = new ArrayList<>();
		GlobalData.getAvailableBeastCards().forEach(beastCard -> {
			boolean beastStrengthPlusOne = actionsTokens.get(ActionType.HUNTING_ACTION).contains(TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
			if (beastStrengthPlusOne) {
				HuntingAction.setBeastStrengthPlusOne(true);
			}
			huntingActions.add(new HuntingAction(ActionType.HUNTING_ACTION));
		});
		return huntingActions;
	}

	private List<Action> prepareAllBuildingActions() {
		List<Action> buildingActions = new ArrayList<>();
		GlobalData.getIdeas().forEach(ideaCard -> buildingActions.add(new BuildingAction(ActionType.BUILDING_ACTION, ideaCard)));

		int charactersNumber = GlobalData.getMainCharactersNumber();
		Arrays.stream(IdeaType.values())
				.filter(ideaType -> ideaType.toString().startsWith("PARAM_"))
				.forEach(ideaType -> {
					if (ideaType == IdeaType.PARAM_SHELTER) {
						if (!GlobalData.isShelter()) {
							buildingActions.add(new BuildingAction(ActionType.BUILDING_ACTION, ideaType, charactersNumber, needOneMoreWoodForBuildingParamsAction));
						}
					} else {
						buildingActions.add(new BuildingAction(ActionType.BUILDING_ACTION, ideaType, charactersNumber, needOneMoreWoodForBuildingParamsAction));
					}
				});

		return buildingActions;
	}

	private List<Action> prepareAllResourcesActions() {
		List<Action> resourcesActions = new ArrayList<>();
		GlobalData.getBoard().getTilePositionIdToIslandTile().forEach((position, islandTile) -> {
			if (islandTile != null && islandTile != GlobalData.getCamp()) {
				ResourceType leftSource = islandTile.getLeftSource();
				ResourceType rightSource = islandTile.getRightSource();
				if (leftSource == ResourceType.WOOD) {
					int woodAmount = islandTile.isEndOfLeftSource() ? 0 : 1;
					woodAmount += actionsTokens.get(ActionType.RESOURCES_ACTION).contains(TokenType.ONE_MORE_WOOD_TOKEN) ? 1 : 0;
					if (woodAmount > 0) {
						resourcesActions.add(new ResourcesAction(ActionType.RESOURCES_ACTION, leftSource, islandTile, woodAmount));
					}
				} else if (leftSource.toString().startsWith("FOOD_")) {
					int foodAmount = islandTile.isEndOfLeftSource() ? 0 : 1;
					foodAmount += actionsTokens.get(ActionType.RESOURCES_ACTION).contains(TokenType.ONE_MORE_FOOD_TOKEN) ? 1 : 0;
					if (foodAmount > 0) {
						resourcesActions.add(new ResourcesAction(ActionType.RESOURCES_ACTION, leftSource, islandTile, foodAmount));
					}
				}
				if (rightSource == ResourceType.WOOD) {
					int woodAmount = islandTile.isEndOfRightSource() ? 0 : 1;
					woodAmount += actionsTokens.get(ActionType.RESOURCES_ACTION).contains(TokenType.ONE_MORE_WOOD_TOKEN) ? 1 : 0;
					if (woodAmount > 0) {
						resourcesActions.add(new ResourcesAction(ActionType.RESOURCES_ACTION, rightSource, islandTile, woodAmount));
					}
				} else if (rightSource.toString().startsWith("FOOD_")) {
					int foodAmount = islandTile.isEndOfRightSource() ? 0 : 1;
					foodAmount += actionsTokens.get(ActionType.RESOURCES_ACTION).contains(TokenType.ONE_MORE_FOOD_TOKEN) ? 1 : 0;
					if (foodAmount > 0) {
						resourcesActions.add(new ResourcesAction(ActionType.RESOURCES_ACTION, rightSource, islandTile, foodAmount));
					}
				}
			}
		});
		return resourcesActions;
	}

	private List<Action> prepareAllExplorationActions() {
		List<Action> explorationActions = new ArrayList<>();
		GlobalData.getBoard().getTilePositionIdToIslandTile().forEach((position, islandTile) -> {
			if (islandTile == null) {
				explorationActions.add(new ExplorationAction(ActionType.EXPLORATION_ACTION, position));
			}
		});
		return explorationActions;
	}

	private void updateActionsAvailability() {
		boolean oneMoreWood = actionsTokens.get(ActionType.BUILDING_ACTION).contains(TokenType.ONE_MORE_WOOD_TOKEN);
		allActions.forEach(action -> {
			boolean possibleToPerform = RequirementsValidator.checkAvailability(action, tmpResources, oneMoreWood);
			if (possibleToPerform) {
				action.setPossibleToPerform(true);
			}
		});
	}

	private int calculateMinMarkersNumber(Action action) { // TODO: 2018-12-11 Optymalizacja
		int markersNumber = 0;
		if (action instanceof ThreatAction || action instanceof CampOrderingAction || action instanceof RestingAction) {
			markersNumber += 1;
		} else if (action instanceof HuntingAction) {
			markersNumber += 2;
		} else if (action instanceof BuildingAction) {
			BuildingAction buildingAction = (BuildingAction) action;
			if (buildingAction.getIdeaType() == IdeaType.PARAM_SHELTER) {
				markersNumber += GlobalData.getBoard().getDistancesBetweenTilesPositions().get(Arrays.asList(
						GlobalData.getBoard().getIslandTileToTilePosition().get(GlobalData.getCamp()),
						GlobalData.getBoard().getIslandTileToTilePosition().get(buildingAction.getPlaceForShelter())
				));
				markersNumber += markersNumber == 0 ? 1 : 0;
				if (buildingAction.getPlaceForShelter().getTokens().contains(TokenType.ONE_MORE_CHARACTER_MARKER_TOKEN)) {
					markersNumber += 1;
				}
			} else {
				markersNumber += 1;
			}
		} else if (action instanceof ResourcesAction) {
			ResourcesAction resourcesAction = (ResourcesAction) action;
			markersNumber += GlobalData.getBoard().getDistancesBetweenTilesPositions().get(Arrays.asList(
					GlobalData.getBoard().getIslandTileToTilePosition().get(GlobalData.getCamp()),
					GlobalData.getBoard().getIslandTileToTilePosition().get(resourcesAction.getIslandTile())
			));
			markersNumber += markersNumber == 0 ? 1 : 0;
			if (resourcesAction.getIslandTile().getTokens().contains(TokenType.ONE_MORE_CHARACTER_MARKER_TOKEN)) {
				markersNumber += 1;
			}
		} else if (action instanceof ExplorationAction) {
			ExplorationAction explorationAction = (ExplorationAction) action;
			markersNumber += GlobalData.getBoard().getDistancesBetweenTilesPositions().get(Arrays.asList(
					GlobalData.getBoard().getIslandTileToTilePosition().get(GlobalData.getCamp()),
					explorationAction.getPositionOnBoard()
			));
			markersNumber += markersNumber == 0 ? 1 : 0;
		}
		boolean oneMoreCharacterMarker = actionsTokens.get(action.getActionType()).contains(TokenType.ONE_MORE_CHARACTER_MARKER_TOKEN);
		if (oneMoreCharacterMarker) {
			markersNumber += 1;
			actionsTokens.get(action.getActionType()).remove(TokenType.ONE_MORE_CHARACTER_MARKER_TOKEN);
		}
		return markersNumber;
	}

	private void prepareAction(Action chosenAction, List<Marker> chosenMarkers, int charactersNumber) {
		chosenAction.setAssignedMarkers(chosenMarkers);
		actionsToPerform.add(chosenAction);
		allActions.remove(chosenAction);
		if (chosenAction.isMultipleAction()) {
			Action newAction = null;
			if (chosenAction instanceof CampOrderingAction) {
				newAction = new CampOrderingAction(ActionType.CAMP_ACTION);
			} else if (chosenAction instanceof RestingAction) {
				newAction = new RestingAction(ActionType.REST_ACTION);
			} else if (chosenAction instanceof BuildingAction) {
				BuildingAction buildingAction = (BuildingAction) chosenAction;
				newAction = new BuildingAction(ActionType.BUILDING_ACTION,
						buildingAction.getIdeaType(),
						charactersNumber,
						needOneMoreWoodForBuildingParamsAction
				);
			}
			allActions.add(newAction);
		}
	}

	private void updateTmpResources(Action action) {
		int tmpWood = tmpResources.getWoodAmount();
		int tmpFur = tmpResources.getFurAmount();
		int tmpFood = tmpResources.getFoodAmount();
		int tmpLongTermFood = tmpResources.getLongTermFoodAmount();
		int requiredWood = action.getRequirements().getRequiredResources().getWoodAmount();
		int requiredFur = action.getRequirements().getRequiredResources().getFurAmount();
		int requiredFood = action.getRequirements().getRequiredResources().getFoodAmount();
		int requiredLongTermFood = action.getRequirements().getRequiredResources().getLongTermFoodAmount();
		tmpResources.setWoodAmount(tmpWood - requiredWood);
		tmpResources.setFurAmount(tmpFur - requiredFur);
		tmpResources.setFoodAmount(tmpFood - requiredFood);
		tmpResources.setLongTermFoodAmount(tmpLongTermFood - requiredLongTermFood);
	}

	@Override
	public void runPhase() {
		actionsToPerform.forEach(action -> {
			Marker leaderMarker = action.getAssignedMarkers().get(0);
			Character leader = GlobalData.getCharacters().stream()
					.filter(character -> character.getMarkers().contains(leaderMarker))
					.findFirst().get();

			handleBeastTokenOnExplorationAction(action, leader);
			handleBeastTokenOnIslandTile(action, leader);

			if (action instanceof BuildingAction || action instanceof ResourcesAction || action instanceof ExplorationAction) {
				StringBuilder sb = new StringBuilder();
				DicesGroupType dicesGroup = null;
				action.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", "")).append(" "));
				if (action instanceof BuildingAction) {
					logger.info("++ Action: BUILDING, Characters: " + sb.toString() + ", Idea: " + ((BuildingAction) action).getIdeaType().toString());
					dicesGroup = DicesGroupType.BUILDING_DICES;
				} else if (action instanceof ResourcesAction) {
					logger.info("++ Action: GATHERING RESOURCE, Characters: " + sb.toString() + ", Resource: " + ((ResourcesAction) action).getSource().toString());
					dicesGroup = DicesGroupType.RESOURCES_DICES;
				} else {
					logger.info("++ Action: EXPLORATION, Characters: " + sb.toString() + ", Position on board: " + ((ExplorationAction) action).getPositionOnBoard());
					dicesGroup = DicesGroupType.EXPLORATION_DICES;
				}

				int minMarkersNumber = 1; // TODO: 2018-11-30
				if (action.getAssignedMarkers().size() > minMarkersNumber) {
					action.performTheAction(leader);
				} else {
					boolean rollAgainIfSuccess = actionsTokens.get(action.getActionType()).contains(TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
					List<DiceWallType> results = action.rollDices(dicesGroup, rollAgainIfSuccess);
					if (rollAgainIfSuccess) {
						actionsTokens.get(action.getActionType()).remove(TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
					}
					if (results.contains(DiceWallType.WOUND)) {
						MethodRepository.changeLivesLevel(-1, leader);
					}
					if (results.contains(DiceWallType.SUCCESS)) {
						action.performTheAction(leader);
					} else if (results.contains(DiceWallType.DETERMINATION)) {
						MethodRepository.changeDeterminationLevel(2, leader);
					}
					if (results.contains(DiceWallType.ADVENTURE)) {
						if (leader instanceof MainCharacter) {
							AdventureCard adventureCard = GlobalData.getDecks().getBuildingAdventureCardsDeck().removeFirst();
							adventureCard.handleAdventure();
						} else {
							MethodRepository.changeLivesLevel(-1, leader);
						}
						if (actionsTokens.get(action.getActionType()).contains(TokenType.ADVENTURE_TOKEN)) {
							actionsTokens.get(action.getActionType()).remove(TokenType.ADVENTURE_TOKEN);
						}
					}
				}
				if (actionsTokens.get(action.getActionType()).contains(TokenType.ADVENTURE_TOKEN)) {
					actionsTokens.get(action.getActionType()).remove(TokenType.ADVENTURE_TOKEN);
					AdventureCard adventureCard = GlobalData.getDecks().getBuildingAdventureCardsDeck().removeFirst();
					adventureCard.handleAdventure();
				}
			} else {
				action.performTheAction(leader);
			}
		});

		Resources availableResources = GlobalData.getAvailableResources();
		Resources futureResources = GlobalData.getFutureResources();
		MethodRepository.changeFoodLevel(futureResources.getFoodAmount());
		MethodRepository.changeLongTermFoodLevel(futureResources.getLongTermFoodAmount());
		MethodRepository.changeWoodLevel(futureResources.getWoodAmount());
		MethodRepository.changeFurLevel(futureResources.getFurAmount());
		availableResources.getDiscoveryTokens().addAll(availableResources.getDiscoveryTokens());

		if (GlobalData.isUnavailableInventions()) {
			GlobalData.setUnavailableInventions(false);
		}
	}

	private void handleBeastTokenOnIslandTile(Action action, Character leader) {
		if ((action instanceof BuildingAction && ((BuildingAction) action).getPlaceForShelter().getTokens().contains(TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN))
				|| (action instanceof ResourcesAction && ((ResourcesAction) action).getIslandTile().getTokens().contains(TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN))
			/*|| todo Warunek na specjalną eksplorację*/) {
			if (GlobalData.getLevels().getWeaponLevel() < 1) {
				MethodRepository.changeLivesLevel(-1, leader);
			}
		}
	}

	private void handleBeastTokenOnExplorationAction(Action action, Character leader) {
		if (action instanceof ExplorationAction && ActionPhase.getActionsTokens().get(ActionType.EXPLORATION_ACTION).contains(TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN)) {
			if (GlobalData.getLevels().getWeaponLevel() < 1) {
				MethodRepository.changeLivesLevel(-1, leader);
			}
			ActionPhase.getActionsTokens().get(ActionType.EXPLORATION_ACTION).remove(TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
		}
	}
}
