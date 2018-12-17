package engine.action;

import model.character.Character;
import model.data.GlobalData;
import model.elements.tiles.IslandTile;
import model.enums.ActionType;
import model.enums.elements.ResourceType;

public class ResourcesAction extends Action {
    private ResourceType source;
    private IslandTile islandTile;
    private int resourceAmount;

    public ResourcesAction(ActionType actionType, ResourceType source, IslandTile islandTile, int resourceAmount) {
		super(actionType);
        this.source = source;
        this.islandTile = islandTile;
        this.resourceAmount = resourceAmount;
    }

    public ResourceType getSource() {
        return source;
    }

    public void setSource(ResourceType source) {
        this.source = source;
    }

    public IslandTile getIslandTile() {
        return islandTile;
    }

    public void setIslandTile(IslandTile islandTile) {
        this.islandTile = islandTile;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public void setResourceAmount(int resourceAmount) {
        this.resourceAmount = resourceAmount;
    }

	@Override
    public void performTheAction(Character leader) {
        if (source.toString().startsWith("FOOD_")) {
            GlobalData.getFutureResources().setFoodAmount(GlobalData.getFutureResources().getFoodAmount() + resourceAmount);
        } else if (source == ResourceType.WOOD) {
            GlobalData.getFutureResources().setWoodAmount(GlobalData.getFutureResources().getWoodAmount() + resourceAmount);
		}
	}
}
