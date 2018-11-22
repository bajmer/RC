package model.requirements;

import model.elements.cards.IdeaCard;
import model.enums.TerrainType;

public class Requirements {
    private TerrainType requiredTerrain;
    private IdeaCard requiredItem;
    private int requiredWoods;
    private int requiredHides;

    public Requirements(TerrainType requiredTerrain, IdeaCard requiredItem, int requiredWoods, int requiredHides) {
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

    public IdeaCard getRequiredItem() {
        return requiredItem;
    }

    public void setRequiredItem(IdeaCard requiredItem) {
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
