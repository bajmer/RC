package engine.action;

import model.cards.AdventureCard;
import model.data.GlobalData;
import model.elements.Marker;
import model.elements.tiles.IslandTile;
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
    private IslandTile islandTile;

    public ResourcesAction(ActionType actionType, ResourceType resourceType, IslandTile islandTile) {
		super(actionType);
		this.resourceType = resourceType;
        this.islandTile = islandTile;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

    public IslandTile getIslandTile() {
        return islandTile;
    }

    public void setIslandTile(IslandTile islandTile) {
        this.islandTile = islandTile;
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
                globalData.getDecks().setTokenOnResourcesAdventureDeck(false);
				AdventureCard adventureCard = globalData.getDecks().getResourcesAdventureCardsDeck().removeFirst();
                adventureCard.handleAdventure();
			}
		} else {
			handleSuccess(globalData);
		}
        if (globalData.getDecks().isTokenOnResourcesAdventureDeck()) {
            globalData.getDecks().setTokenOnResourcesAdventureDeck(false);
            AdventureCard adventureCard = globalData.getDecks().getResourcesAdventureCardsDeck().removeFirst();
            adventureCard.handleAdventure();
        }
	}

	private void handleSuccess(GlobalData globalData) {
        int amount = 1; // TODO: 2018-11-30 Can be modified
		if (resourceType == ResourceType.FOOD) {
			globalData.getFutureResources().setFoodAmount(globalData.getFutureResources().getFoodAmount() + amount);
		} else if (resourceType == ResourceType.WOOD) {
			globalData.getFutureResources().setWoodAmount(globalData.getFutureResources().getWoodAmount() + amount);
		}
	}
}
