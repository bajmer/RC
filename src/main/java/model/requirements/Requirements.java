package model.requirements;

import model.data.GameParams;
import model.elements.cards.IdeaCard;
import model.enums.TerrainType;
import model.resources.Resources;

import java.util.ArrayList;
import java.util.List;

public class Requirements {
    private List<TerrainType> requiredTerrains;
    private List<IdeaCard> requiredItems;
    private Resources requiredResources;
    private GameParams requiredGameParams;
    private boolean requiredShelter;

    public Requirements() {
        this.requiredTerrains = new ArrayList<>();
        this.requiredItems = new ArrayList<>();
        this.requiredResources = new Resources();
        this.requiredGameParams = new GameParams();
        this.requiredShelter = false;
    }

    public Requirements(List<TerrainType> requiredTerrains, List<IdeaCard> requiredItems, Resources requiredResources, GameParams requiredGameParams, boolean requiredShelter) {
        this.requiredTerrains = requiredTerrains;
        this.requiredItems = requiredItems;
        this.requiredResources = requiredResources;
        this.requiredGameParams = requiredGameParams;
        this.requiredShelter = requiredShelter;
    }

    public List<TerrainType> getRequiredTerrains() {
        return requiredTerrains;
    }

    public void setRequiredTerrains(List<TerrainType> requiredTerrains) {
        this.requiredTerrains = requiredTerrains;
    }

    public List<IdeaCard> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(List<IdeaCard> requiredItems) {
        this.requiredItems = requiredItems;
    }

    public Resources getRequiredResources() {
        return requiredResources;
    }

    public void setRequiredResources(Resources requiredResources) {
        this.requiredResources = requiredResources;
    }

    public GameParams getRequiredGameParams() {
        return requiredGameParams;
    }

    public void setRequiredGameParams(GameParams requiredGameParams) {
        this.requiredGameParams = requiredGameParams;
    }

    public boolean isRequiredShelter() {
        return requiredShelter;
    }

    public void setRequiredShelter(boolean requiredShelter) {
        this.requiredShelter = requiredShelter;
    }
}
