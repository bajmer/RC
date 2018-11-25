package model.requirements;

import model.enums.TerrainType;
import model.enums.cards.IdeaType;
import model.resources.Resources;

import java.util.List;

public class Requirements {
    private List<TerrainType> requiredTerrains;
    private List<IdeaType> requiredItems;
    private List<Resources> requiredResources;
    private List<Integer> requiredLevels;
    private boolean requiredShelter;
    private int minNumberOfMarkers;
    private int maxNumberOfMarkers;
    private int requiredNumberOfDeterminations;

    public Requirements(List<TerrainType> requiredTerrains, List<IdeaType> requiredItems, List<Resources> requiredResources, List<Integer> requiredLevels, boolean requiredShelter, int minNumberOfMarkers, int maxNumberOfMarkers, int requiredNumberOfDeterminations) {
        this.requiredTerrains = requiredTerrains;
        this.requiredItems = requiredItems;
        this.requiredResources = requiredResources;
        this.requiredLevels = requiredLevels;
        this.requiredShelter = requiredShelter;
        this.minNumberOfMarkers = minNumberOfMarkers;
        this.maxNumberOfMarkers = maxNumberOfMarkers;
        this.requiredNumberOfDeterminations = requiredNumberOfDeterminations;
    }

    public List<TerrainType> getRequiredTerrains() {
        return requiredTerrains;
    }

    public void setRequiredTerrains(List<TerrainType> requiredTerrains) {
        this.requiredTerrains = requiredTerrains;
    }

    public List<IdeaType> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(List<IdeaType> requiredItems) {
        this.requiredItems = requiredItems;
    }

    public List<Resources> getRequiredResources() {
        return requiredResources;
    }

    public void setRequiredResources(List<Resources> requiredResources) {
        this.requiredResources = requiredResources;
    }

    public List<Integer> getRequiredLevels() {
        return requiredLevels;
    }

    public void setRequiredLevels(List<Integer> requiredLevels) {
        this.requiredLevels = requiredLevels;
    }

    public boolean isRequiredShelter() {
        return requiredShelter;
    }

    public void setRequiredShelter(boolean requiredShelter) {
        this.requiredShelter = requiredShelter;
    }

    public int getMinNumberOfMarkers() {
        return minNumberOfMarkers;
    }

    public void setMinNumberOfMarkers(int minNumberOfMarkers) {
        this.minNumberOfMarkers = minNumberOfMarkers;
    }

    public int getMaxNumberOfMarkers() {
        return maxNumberOfMarkers;
    }

    public void setMaxNumberOfMarkers(int maxNumberOfMarkers) {
        this.maxNumberOfMarkers = maxNumberOfMarkers;
    }

    public int getRequiredNumberOfDeterminations() {
        return requiredNumberOfDeterminations;
    }

    public void setRequiredNumberOfDeterminations(int requiredNumberOfDeterminations) {
        this.requiredNumberOfDeterminations = requiredNumberOfDeterminations;
    }
}
