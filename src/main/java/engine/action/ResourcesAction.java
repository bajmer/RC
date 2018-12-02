package engine.action;

import model.data.GlobalData;
import model.elements.Marker;
import model.elements.cards.AdventureCard;
import model.enums.ActionType;
import model.enums.elements.ResourceType;
import model.enums.elements.dices.DiceWallType;
import model.enums.elements.dices.DicesGroupType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ResourcesAction extends Action {
	private Logger logger = LogManager.getLogger(ResourcesAction.class);

	private ResourceType resourceType;
	private int positionOnBoard;

	public ResourcesAction(ActionType actionType, ResourceType resourceType, int positionOnBoard) {
		super(actionType);
		this.resourceType = resourceType;
		this.positionOnBoard = positionOnBoard;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public int getPositionOnBoard() {
		return positionOnBoard;
	}

	public void setPositionOnBoard(int positionOnBoard) {
		this.positionOnBoard = positionOnBoard;
	}

	@Override
	public void performTheAction(GlobalData globalData) {
		StringBuilder sb = new StringBuilder();
		super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", "")).append(" "));
		logger.info("++ Action: GATHERING RESOURCE, Characters: " + sb.toString() + ", Resource: " + resourceType.toString());

		int minMarkersNumber = 1; // TODO: 2018-11-30
		if (super.getAssignedMarkers().size() == minMarkersNumber) {
			List<DiceWallType> results = super.rollDices(globalData, DicesGroupType.RESOURCES_DICES);
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
				AdventureCard adventureCard = globalData.getDecks().getResourcesAdventureCardsDeck().removeFirst();
				// TODO: 2018-11-30 handle resource adventure card
			}
		} else {
			handleSuccess(globalData);
		}

	}

	private void handleSuccess(GlobalData globalData) {
		int amount = 1; // TODO: 2018-11-30 Can be modyfied
		if (resourceType == ResourceType.FOOD) {
			globalData.getFutureResources().setFoodAmount(globalData.getFutureResources().getFoodAmount() + amount);
		} else if (resourceType == ResourceType.WOOD) {
			globalData.getFutureResources().setWoodAmount(globalData.getFutureResources().getWoodAmount() + amount);
		}
	}
}
