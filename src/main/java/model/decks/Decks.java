package model.decks;

import model.cards.*;
import model.elements.tiles.DiscoveryToken;
import model.elements.tiles.IslandTile;

import java.util.LinkedList;

public class Decks {
    private LinkedList<EventCard> eventCardsDeck;
    private LinkedList<AdventureCard> buildingAdventureCardsDeck;
    private LinkedList<AdventureCard> explorationAdventureCardsDeck;
    private LinkedList<AdventureCard> resourcesAdventureCardsDeck;
    private LinkedList<BeastCard> beastCardsDeck;
    private LinkedList<MysteryCard> mysteryCardsDeck;
    private LinkedList<DiscoveryToken> discoveryTokensStack;
    private LinkedList<IslandTile> islandTilesStack;
    private LinkedList<IdeaCard> ideaCardsDeck;
    private LinkedList<StartingItemCard> startingItemsDeck;

    public Decks(LinkedList<EventCard> eventCardsDeck, LinkedList<AdventureCard> buildingAdventureCardsDeck, LinkedList<AdventureCard> explorationAdventureCardsDeck, LinkedList<AdventureCard> resourcesAdventureCardsDeck, LinkedList<BeastCard> beastCardsDeck, LinkedList<MysteryCard> mysteryCardsDeck, LinkedList<DiscoveryToken> discoveryTokensStack, LinkedList<IslandTile> islandTilesStack, LinkedList<IdeaCard> ideaCardsDeck, LinkedList<StartingItemCard> startingItemsDeck) {
        this.eventCardsDeck = eventCardsDeck;
        this.buildingAdventureCardsDeck = buildingAdventureCardsDeck;
        this.explorationAdventureCardsDeck = explorationAdventureCardsDeck;
        this.resourcesAdventureCardsDeck = resourcesAdventureCardsDeck;
        this.beastCardsDeck = beastCardsDeck;
        this.mysteryCardsDeck = mysteryCardsDeck;
        this.discoveryTokensStack = discoveryTokensStack;
        this.islandTilesStack = islandTilesStack;
        this.ideaCardsDeck = ideaCardsDeck;
        this.startingItemsDeck = startingItemsDeck;
    }

    public LinkedList<EventCard> getEventCardsDeck() {
        return eventCardsDeck;
    }

    public void setEventCardsDeck(LinkedList<EventCard> eventCardsDeck) {
        this.eventCardsDeck = eventCardsDeck;
    }

    public LinkedList<AdventureCard> getBuildingAdventureCardsDeck() {
        return buildingAdventureCardsDeck;
    }

    public void setBuildingAdventureCardsDeck(LinkedList<AdventureCard> buildingAdventureCardsDeck) {
        this.buildingAdventureCardsDeck = buildingAdventureCardsDeck;
    }

    public LinkedList<AdventureCard> getExplorationAdventureCardsDeck() {
        return explorationAdventureCardsDeck;
    }

    public void setExplorationAdventureCardsDeck(LinkedList<AdventureCard> explorationAdventureCardsDeck) {
        this.explorationAdventureCardsDeck = explorationAdventureCardsDeck;
    }

    public LinkedList<AdventureCard> getResourcesAdventureCardsDeck() {
        return resourcesAdventureCardsDeck;
    }

    public void setResourcesAdventureCardsDeck(LinkedList<AdventureCard> resourcesAdventureCardsDeck) {
        this.resourcesAdventureCardsDeck = resourcesAdventureCardsDeck;
    }

    public LinkedList<BeastCard> getBeastCardsDeck() {
        return beastCardsDeck;
    }

    public void setBeastCardsDeck(LinkedList<BeastCard> beastCardsDeck) {
        this.beastCardsDeck = beastCardsDeck;
    }

    public LinkedList<MysteryCard> getMysteryCardsDeck() {
        return mysteryCardsDeck;
    }

    public void setMysteryCardsDeck(LinkedList<MysteryCard> mysteryCardsDeck) {
        this.mysteryCardsDeck = mysteryCardsDeck;
    }

    public LinkedList<DiscoveryToken> getDiscoveryTokensStack() {
        return discoveryTokensStack;
    }

    public void setDiscoveryTokensStack(LinkedList<DiscoveryToken> discoveryTokensStack) {
        this.discoveryTokensStack = discoveryTokensStack;
    }

    public LinkedList<IslandTile> getIslandTilesStack() {
        return islandTilesStack;
    }

    public void setIslandTilesStack(LinkedList<IslandTile> islandTilesStack) {
        this.islandTilesStack = islandTilesStack;
    }

    public LinkedList<IdeaCard> getIdeaCardsDeck() {
        return ideaCardsDeck;
    }

    public void setIdeaCardsDeck(LinkedList<IdeaCard> ideaCardsDeck) {
        this.ideaCardsDeck = ideaCardsDeck;
    }

    public LinkedList<StartingItemCard> getStartingItemsDeck() {
        return startingItemsDeck;
    }

    public void setStartingItemsDeck(LinkedList<StartingItemCard> startingItemsDeck) {
        this.startingItemsDeck = startingItemsDeck;
    }
}
