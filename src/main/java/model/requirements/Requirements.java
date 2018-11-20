package model.requirements;

import model.elements.cards.InventionCard;
import model.enums.TerrainType;

public class Requirements {
    private TerrainType requiredTerrain;
    private InventionCard requiredItem;
    private int requiredWoods;
    private int requiredHides;

    public Requirements(TerrainType requiredTerrain, InventionCard requiredItem, int requiredWoods, int requiredHides) {
        this.requiredTerrain = requiredTerrain;
        this.requiredItem = requiredItem;
        this.requiredWoods = requiredWoods;
        this.requiredHides = requiredHides;
    }

    public TerrainType getRequiredTerrain() {
        return requiredTerrain;
    }

    public void setRequiredTerrain(TerrainType requiredTerrain) {
        this.requiredTerrain = requiredTerrain;
    }

    public InventionCard getRequiredItem() {
        return requiredItem;
    }

    public void setRequiredItem(InventionCard requiredItem) {
        this.requiredItem = requiredItem;
    }

    public int getRequiredWoods() {
        return requiredWoods;
    }

    public void setRequiredWoods(int requiredWoods) {
        this.requiredWoods = requiredWoods;
    }

    public int getRequiredHides() {
        return requiredHides;
    }

    public void setRequiredHides(int requiredHides) {
        this.requiredHides = requiredHides;
    }
}
