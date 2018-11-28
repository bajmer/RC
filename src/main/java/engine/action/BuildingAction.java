package engine.action;

import model.elements.cards.IdeaCard;
import model.enums.ActionType;
import model.enums.cards.IdeaType;
import model.requirements.Requirements;

import java.util.Arrays;

public class BuildingAction extends Action {

	private IdeaCard ideaCard;
	private IdeaType ideaType;
	private int characterNumbers = 0;

	public BuildingAction(ActionType actionType, IdeaCard ideaCard) {
		super(actionType);
		this.ideaCard = ideaCard;
		this.ideaType = ideaCard.getIdea();
		super.setRequirements(createRequirements());
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

	private Requirements createRequirements() {
		Requirements requirements = new Requirements();
		switch (ideaType) {
			case BOW:
				break;
			case BRICKS:
				break;
			case DAM:
				break;
			case FIRE:
				break;
			case KNIFE:
				break;
			case MAP:
				break;
			case POT:
				break;
			case ROPE:
				break;
			case SHOVEL:
				break;
			case SNARE:
				break;
			case FIREPLACE:
				break;
			case SHORTCUT:
				break;
			case SPEAR:
				break;
			case BASKET:
				break;
			case BED:
				break;
			case BELTS:
				break;
			case CELLAR:
				break;
			case CORRAL:
				break;
			case CURE:
				break;
			case DIARY:
				break;
			case DRUMS:
				break;
			case FURNACE:
				break;
			case LANTERN:
				break;
			case MOAT:
				break;
			case PIT:
				break;
			case RAFT:
				break;
			case SACK:
				break;
			case SHIELD:
				break;
			case SLING:
				break;
			case WALL:
				break;
			case SCN_HATCHET:
				break;
			case SCN_MAST:
				break;
			case SCN_SACRED_BELL:
				break;
			case SCN_CROSS:
				break;
			case SCN_JENNY_RAFT:
				break;
			case SCN_LIFEBOAT:
				break;
			case SCN_BALLISTA:
				break;
			case SCN_CANOE:
				break;
			case SCN_GARDEN:
				break;
			case SCN_TRAP:
				break;
			case SCN_TRANSQUELEZER:
				break;
			case PARAM_SHELTER:
				requirements.setRequiredShelter(false);
				requirements.getRequiredResources().setWoodAmount(characterNumbers > 2 ? characterNumbers : 2);
				requirements.getRequiredResources().setHideAmount(characterNumbers > 2 ? characterNumbers - 1 : 1);
				break;
			case PARAM_ROOF:
				requirements.setRequiredShelter(true);
				requirements.getRequiredResources().setWoodAmount(characterNumbers > 2 ? characterNumbers : 2);
				requirements.getRequiredResources().setHideAmount(characterNumbers > 2 ? characterNumbers - 1 : 1);
				break;
			case PARAM_PALISADE:
				requirements.setRequiredShelter(true);
				requirements.getRequiredResources().setWoodAmount(characterNumbers > 2 ? characterNumbers : 2);
				requirements.getRequiredResources().setHideAmount(characterNumbers > 2 ? characterNumbers - 1 : 1);
				break;
			case PARAM_WEAPON:
				requirements.getRequiredResources().setWoodAmount(1);
				break;
		}
		return requirements;
	}

	@Override
	public void performTheAction() {

	}
}
