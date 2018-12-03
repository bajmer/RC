package engine.action;

import model.cards.AdventureCard;
import model.cards.IdeaCard;
import model.data.GlobalData;
import model.elements.Marker;
import model.elements.tiles.IslandTile;
import model.enums.ActionType;
import model.enums.cards.IdeaType;
import model.enums.elements.dices.DiceType;
import model.enums.elements.dices.DiceWallType;
import model.enums.elements.dices.DicesGroupType;
import model.requirements.Requirements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildingAction extends Action {
	private Logger logger = LogManager.getLogger(BuildingAction.class);

	private IdeaCard ideaCard;
	private IdeaType ideaType;
	private int characterNumbers = 0;
	private IslandTile placeForShelter = null;

	public BuildingAction(ActionType actionType, IdeaCard ideaCard) {
		super(actionType);
		this.ideaCard = ideaCard;
		this.ideaType = ideaCard.getIdea();
		super.setRequirements(ideaCard.specifyRequirements());
	}

	public BuildingAction(ActionType actionType, IdeaType ideaType, int characterNumbers) {
		super(actionType);
		this.ideaCard = null;
		this.ideaType = ideaType;
		this.characterNumbers = characterNumbers;
		super.setRequirements(createRequirements());
		if (Arrays.asList(IdeaType.PARAM_ROOF, IdeaType.PARAM_PALISADE, IdeaType.PARAM_WEAPON).contains(ideaType)) {
			super.setMultipleAction(true);
		}
	}

	public IdeaCard getIdeaCard() {
		return ideaCard;
	}

	public void setIdeaCard(IdeaCard ideaCard) {
		this.ideaCard = ideaCard;
	}

	public IdeaType getIdeaType() {
		return ideaType;
	}

	public void setIdeaType(IdeaType ideaType) {
		this.ideaType = ideaType;
	}

	public int getCharacterNumbers() {
		return characterNumbers;
	}

	public void setCharacterNumbers(int characterNumbers) {
		this.characterNumbers = characterNumbers;
	}

	public IslandTile getPlaceForShelter() {
		return placeForShelter;
	}

	public void setPlaceForShelter(IslandTile placeForShelter) {
		this.placeForShelter = placeForShelter;
	}

	private Requirements createRequirements() {
		Requirements requirements = new Requirements();
		switch (ideaType) {
			case PARAM_SHELTER:
				requirements.setRequiredShelter(false);
				requirements.getRequiredResources().setWoodAmount(characterNumbers > 2 ? characterNumbers : 2);
				requirements.getRequiredResources().setFurAmount(characterNumbers > 2 ? characterNumbers - 1 : 1);
				break;
			case PARAM_ROOF:
				requirements.setRequiredShelter(true);
				requirements.getRequiredResources().setWoodAmount(characterNumbers > 2 ? characterNumbers : 2);
				requirements.getRequiredResources().setFurAmount(characterNumbers > 2 ? characterNumbers - 1 : 1);
				break;
			case PARAM_PALISADE:
				requirements.setRequiredShelter(true);
				requirements.getRequiredResources().setWoodAmount(characterNumbers > 2 ? characterNumbers : 2);
				requirements.getRequiredResources().setFurAmount(characterNumbers > 2 ? characterNumbers - 1 : 1);
				break;
			case PARAM_WEAPON:
				requirements.getRequiredResources().setWoodAmount(1);
				break;
		}
		return requirements;
	}

	@Override
	public void performTheAction(GlobalData globalData) {
		StringBuilder sb = new StringBuilder();
		super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", "")).append(" "));
		logger.info("++ Action: BUILDING, Characters: " + sb.toString() + ", Idea: " + ideaType.toString());

		int minMarkersNumber = 1; // TODO: 2018-11-30
		if (super.getAssignedMarkers().size() == minMarkersNumber) {
			List<DiceWallType> results = rollBuildingDices(globalData);
			Marker leaderMarker = super.getAssignedMarkers().get(0);
			if (results.contains(DiceWallType.WOUND)) {
				globalData.getCharacters().stream()
						.filter(character -> character.getMarkers().contains(leaderMarker))
						.findFirst().get().changeLivesLevel(-1);
			}
			if (results.contains(DiceWallType.SUCCESS)) {
				handleSuccess(globalData);
			} else if (results.contains(DiceWallType.DETERMINATION)) {
				globalData.getCharacters().stream()
						.filter(character -> character.getMarkers().contains(leaderMarker))
						.findFirst().get().changeDeterminationLevel(2);
			}
			if (results.contains(DiceWallType.ADVENTURE)) {
				globalData.getDecks().setTokenOnBuildingAdventureDeck(false);
				AdventureCard adventureCard = globalData.getDecks().getBuildingAdventureCardsDeck().removeFirst();
				adventureCard.handleAdventure();
			}
		} else {
			handleSuccess(globalData);
		}

		if (globalData.getDecks().isTokenOnBuildingAdventureDeck()) {
			globalData.getDecks().setTokenOnBuildingAdventureDeck(false);
			AdventureCard adventureCard = globalData.getDecks().getBuildingAdventureCardsDeck().removeFirst();
			adventureCard.handleAdventure();
		}
	}

	private List<DiceWallType> rollBuildingDices(GlobalData globalData) {
		List<DiceWallType> results = new ArrayList<>();
		results.add(globalData.getDices().roll(DicesGroupType.BUILDING_DICES, DiceType.SUCCESS));
		results.add(globalData.getDices().roll(DicesGroupType.BUILDING_DICES, DiceType.ADVENTURE));
		results.add(globalData.getDices().roll(DicesGroupType.BUILDING_DICES, DiceType.WOUND));

		StringBuilder sb = new StringBuilder();
		results.forEach(result -> sb.append(result.toString()).append(" "));
		logger.info("BUILDING DICES have been rolled. Results: " + sb.toString());
		return results;
	}

	private void handleSuccess(GlobalData globalData) {
		int requiredWood = super.getRequirements().getRequiredResources().getWoodAmount();
		int requiredFurs = super.getRequirements().getRequiredResources().getFurAmount();
		int requiredFood = super.getRequirements().getRequiredResources().getFoodAmount();
		int requiredLtFood = super.getRequirements().getRequiredResources().getLongTermFoodAmount();
		globalData.changeWoodLevel(-requiredWood);
		globalData.changeFurLevel(-requiredFurs);
		globalData.changeWoodLevel(-requiredFood);
		globalData.changeLongTermFoodLevel(-requiredLtFood);

		if (ideaCard != null) {
			globalData.getIdeas().remove(ideaCard);
			ideaCard.transformIdeaToItem(globalData);
			globalData.getInventions().add(ideaCard);
		} else {
			switch (ideaType) {
				case PARAM_SHELTER:
					// TODO: 2018-11-30 build shelter
					break;
				case PARAM_ROOF:
					globalData.changeRoofLevel(1);
					break;
				case PARAM_PALISADE:
					globalData.changePalisadeLevel(1);
					break;
				case PARAM_WEAPON:
					globalData.changeWeaponLevel(1, null);
					break;
			}
		}
	}
}
