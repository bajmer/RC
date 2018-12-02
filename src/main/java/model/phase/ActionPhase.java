package model.phase;

import engine.RequirementsValidator;
import engine.action.*;
import model.character.main.MainCharacter;
import model.data.GlobalData;
import model.elements.Marker;
import model.enums.ActionType;
import model.enums.cards.IdeaType;
import model.enums.elements.ResourceType;
import model.resources.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ActionPhase implements Phase {
    private Logger logger = LogManager.getLogger(ActionPhase.class);

    private List<Action> allActions = new ArrayList<>();
    private List<Action> actionsToPerform = new ArrayList<>();
    private List<Marker> allMarkers = new ArrayList<>();
    private Resources tmpResources = new Resources();

    @Override
    public void initializePhase(GlobalData globalData) {
        logger.info("---> Phase: ACTION");

        // Clearing all lists
        allActions.clear();
        actionsToPerform.clear();
        allMarkers.clear();
        tmpResources.setFoodAmount(globalData.getAvailableResources().getFoodAmount());
        tmpResources.setLongTermFoodAmount(globalData.getAvailableResources().getLongTermFoodAmount());
        tmpResources.setWoodAmount(globalData.getAvailableResources().getWoodAmount());
        tmpResources.setFurAmount(globalData.getAvailableResources().getFurAmount());

        // Preparing markers
        globalData.getCharacters().forEach(character -> allMarkers.addAll(character.getMarkers()));
        allMarkers.addAll(globalData.getAvailableActionHelpers());

        // Preparing all actions
        allActions.addAll(prepareAllThreatActions(globalData));
        allActions.addAll(prepareAllHuntingActions(globalData));
        allActions.addAll(prepareAllBuildingActions(globalData));
        allActions.addAll(prepareAllResourcesActions(globalData));
        allActions.addAll(prepareAllExplorationActions(globalData));
        allActions.add(new CampOrderingAction(ActionType.CAMP_ACTION));
        allActions.add(new RestingAction(ActionType.REST_ACTION));

        // Handle actions choosing
        boolean ready = false; // TODO: "Ready" button
        while (!ready) {
            updateActionsAvailability(globalData);
            Action chosenAction = chooseAction();
            int minMarkersNumber = calculateMinMarkersNumber(chosenAction, globalData);
            List<Marker> chosenMarkers = chooseMarkers(chosenAction, minMarkersNumber);
            chosenAction.setAssignedMarkers(chosenMarkers);
            handleAction(chosenAction);
            updateTmpResources(chosenAction);
            ready = true;
        }

        // Sorting actions order
        actionsToPerform.sort(Comparator.comparingInt(Action::getOrderOfExecution));
    }

    private void updateActionsAvailability(GlobalData globalData) {
        allActions.forEach(action -> {
            boolean possibleToPerform = RequirementsValidator.checkAvailability(action, globalData, tmpResources);
            if (possibleToPerform) {
                action.setPossibleToPerform(true);
            }
        });
    }

    private Action chooseAction() {
        List<Action> possibleActions = allActions.stream()
                .filter(Action::isPossibleToPerform)
                .collect(Collectors.toList());

        return possibleActions.get(0); // TODO: 2018-11-28
    }

    private List<Marker> chooseMarkers(Action action, int minMarkersNumber) {
        List<Marker> possibleMarkers = allMarkers.stream()
                .filter(marker -> action.getAcceptedMarkerTypes().contains(marker.getMarkerType()))
                .collect(Collectors.toList());

        return new ArrayList<>(); // TODO: 2018-11-28
    }

    private int calculateMinMarkersNumber(Action action, GlobalData globalData) {
        int number = 0;
        if (action instanceof ThreatAction) {
            number = 1;
        } else if (action instanceof HuntingAction) {
            number = 2;
        } else if (action instanceof BuildingAction) {
            number = 1;
        } else if (action instanceof ResourcesAction) {
            ResourcesAction resourcesAction = (ResourcesAction) action;
            number = globalData.getBoard().getDistancesBetweenTilesPositions().get(new ArrayList<>(Arrays.asList(
                    globalData.getBoard().getIslandTileToTilePosition().get(globalData.getCamp()),
                    resourcesAction.getPositionOnBoard()
            )));
        } else if (action instanceof ExplorationAction) {
            ExplorationAction explorationAction = (ExplorationAction) action;
            number = globalData.getBoard().getDistancesBetweenTilesPositions().get(new ArrayList<>(Arrays.asList(
                    globalData.getBoard().getIslandTileToTilePosition().get(globalData.getCamp()),
                    explorationAction.getPositionOnBoard()
            )));
        } else if (action instanceof CampOrderingAction) {
            number = 1;
        } else if (action instanceof RestingAction) {
            number = 1;
        }
        return number;
    }

    private void handleAction(Action chosenAction) {
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
                        buildingAction.getCharacterNumbers()
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

    private List<Action> prepareAllThreatActions(GlobalData globalData) {
        List<Action> threatActions = new ArrayList<>();
        globalData.getThreatActionCards().forEach(eventCard -> threatActions.add(new ThreatAction(ActionType.THREAT_ACTION, eventCard)));
        return threatActions;
    }

    private List<Action> prepareAllHuntingActions(GlobalData globalData) {
        List<Action> huntingActions = new ArrayList<>();
        globalData.getAvailableBeastCards().forEach(beastCard -> huntingActions.add(new HuntingAction(ActionType.HUNTING_ACTION)));
        return huntingActions;
    }

    private List<Action> prepareAllBuildingActions(GlobalData globalData) {
        List<Action> buildingActions = new ArrayList<>();
        globalData.getIdeas().forEach(ideaCard -> buildingActions.add(new BuildingAction(ActionType.BUILDING_ACTION, ideaCard)));

        int charactersNumber = Math.toIntExact(globalData.getCharacters().stream().filter(character -> character instanceof MainCharacter).count());
        Arrays.stream(IdeaType.values())
                .filter(ideaType -> Arrays.asList(IdeaType.PARAM_ROOF, IdeaType.PARAM_PALISADE, IdeaType.PARAM_WEAPON).contains(ideaType))
                .forEach(ideaType -> buildingActions.add(new BuildingAction(ActionType.BUILDING_ACTION, ideaType, charactersNumber)));
        if (!globalData.isShelter()) {
            buildingActions.add(new BuildingAction(ActionType.BUILDING_ACTION, IdeaType.PARAM_SHELTER, charactersNumber));
        }
        return buildingActions;
    }

    private List<Action> prepareAllResourcesActions(GlobalData globalData) {
        List<Action> resourcesActions = new ArrayList<>();
        globalData.getBoard().getTilePositionIdToIslandTile().forEach((position, islandTile) -> {
            if (islandTile != null && islandTile != globalData.getCamp()) {
                ResourceType leftResource = islandTile.getLeftSquareResource();
                ResourceType rightResource = islandTile.getRightSquareResource();
                if (leftResource != ResourceType.ANIMAL) {
                    resourcesActions.add(new ResourcesAction(ActionType.RESOURCES_ACTION, leftResource, position));
                }
                if (rightResource != ResourceType.ANIMAL) {
                    resourcesActions.add(new ResourcesAction(ActionType.RESOURCES_ACTION, rightResource, position));
                }
            }
        });
        return resourcesActions;
    }

    private List<Action> prepareAllExplorationActions(GlobalData globalData) {
        List<Action> explorationActions = new ArrayList<>();
        globalData.getBoard().getTilePositionIdToIslandTile().forEach((position, islandTile) -> {
            if (islandTile == null) {
                explorationActions.add(new ExplorationAction(ActionType.EXPLORATION_ACTION, position));
            }
        });
        return explorationActions;
    }

    @Override
    public void runPhase(GlobalData globalData) {

        // TODO: 2018-11-23 Handle actions
        actionsToPerform.forEach(action -> action.performTheAction(globalData));
    }
}
