package engine.action;

import engine.MethodRepository;
import model.cards.IdeaCard;
import model.character.Character;
import model.data.GlobalData;
import model.elements.tiles.IslandTile;
import model.enums.ActionType;
import model.enums.cards.IdeaType;
import model.requirements.Requirements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class BuildingAction extends Action {
	private Logger logger = LogManager.getLogger(BuildingAction.class);

	private IdeaCard ideaCard;
	private IdeaType ideaType;
	private IslandTile placeForShelter = null;

	public BuildingAction(ActionType actionType, IdeaCard ideaCard) {
		super(actionType);
		this.ideaCard = ideaCard;
		this.ideaType = ideaCard.getIdea();
		super.setRequirements(ideaCard.specifyRequirements());
	}

	public BuildingAction(ActionType actionType, IdeaType ideaType, int charactersNumber, boolean needOneMoreWoodForBuildingParamsAction) {
		super(actionType);
		this.ideaCard = null;
		this.ideaType = ideaType;
		super.setRequirements(defineBuildingParamsActionsRequirements(charactersNumber, needOneMoreWoodForBuildingParamsAction));
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

	public IslandTile getPlaceForShelter() {
		return placeForShelter;
	}

	public void setPlaceForShelter(IslandTile placeForShelter) {
		this.placeForShelter = placeForShelter;
	}

	private Requirements defineBuildingParamsActionsRequirements(int charactersNumber, boolean needOneMoreWood) {
		Requirements requirements = new Requirements();
		int additionalWood = needOneMoreWood ? 1 : 0;
		int woodAmount = charactersNumber > 2 ? charactersNumber + additionalWood : 2 + additionalWood;
		int furAmount = charactersNumber > 2 ? charactersNumber - 1 : 1;

		switch (ideaType) {
			case PARAM_SHELTER:
				requirements.setRequiredShelter(false);
				requirements.getRequiredResources().setWoodAmount(woodAmount);
				requirements.getRequiredResources().setFurAmount(furAmount);
				break;
			case PARAM_ROOF:
				requirements.setRequiredShelter(true);
				requirements.getRequiredResources().setWoodAmount(woodAmount);
				requirements.getRequiredResources().setFurAmount(furAmount);
				break;
			case PARAM_PALISADE:
				requirements.setRequiredShelter(true);
				requirements.getRequiredResources().setWoodAmount(woodAmount);
				requirements.getRequiredResources().setFurAmount(furAmount);
				break;
			case PARAM_WEAPON:
				requirements.getRequiredResources().setWoodAmount(1 + additionalWood);
				break;
		}
		return requirements;
	}

	@Override
	public void performTheAction(Character leader) {
		int requiredWood = super.getRequirements().getRequiredResources().getWoodAmount();
		int requiredFurs = super.getRequirements().getRequiredResources().getFurAmount();
		int requiredFood = super.getRequirements().getRequiredResources().getFoodAmount();
		int requiredLtFood = super.getRequirements().getRequiredResources().getLongTermFoodAmount();
		MethodRepository.changeWoodLevel(-requiredWood);
		MethodRepository.changeFurLevel(-requiredFurs);
		MethodRepository.changeWoodLevel(-requiredFood);
		MethodRepository.changeLongTermFoodLevel(-requiredLtFood);

		if (ideaCard != null) {
			GlobalData.getIdeas().remove(ideaCard);
			ideaCard.transformIdeaToInvention();
			GlobalData.getInventions().add(ideaCard);
		} else {
			switch (ideaType) {
				case PARAM_SHELTER:
					if (placeForShelter != null) {
						GlobalData.setCamp(placeForShelter);
						GlobalData.setShelter(true);
					}
					break;
				case PARAM_ROOF:
					MethodRepository.changeRoofLevel(1);
					break;
				case PARAM_PALISADE:
					MethodRepository.changePalisadeLevel(1);
					break;
				case PARAM_WEAPON:
					MethodRepository.changeWeaponLevel(1, null);
					break;
			}
		}
	}
}
