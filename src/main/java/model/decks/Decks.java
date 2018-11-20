package model.decks;

import model.elements.cards.BeastCard;
import model.elements.cards.EventCard;
import model.elements.cards.InventionCard;
import model.elements.cards.adventure.BuildingAdventureCard;
import model.elements.cards.adventure.ExplorationAdventureCard;
import model.elements.cards.adventure.ResourcesAdventureCard;
import model.elements.cards.mystery.MysteryCard;
import model.elements.tiles.DiscoveryToken;
import model.elements.tiles.IslandTile;

import java.util.LinkedList;

public class Decks {
    private LinkedList<EventCard> eventCardsDeck;
    private LinkedList<BuildingAdventureCard> buildingAdventureCardsDeck;
    private LinkedList<ExplorationAdventureCard> explorationAdventureCardsDeck;
    private LinkedList<ResourcesAdventureCard> resourcesAdventureCardsDeck;
    private LinkedList<BeastCard> beastCardsDeck;
    private LinkedList<MysteryCard> mysteryCardsDeck;
    private LinkedList<DiscoveryToken> discoveryTokensStack;
    private LinkedList<IslandTile> islandTilesStack;
    private LinkedList<InventionCard> inventionCardsDeck;

    public Decks(LinkedList<EventCard> eventCardsDeck, LinkedList<BuildingAdventureCard> buildingAdventureCardsDeck, LinkedList<ExplorationAdventureCard> explorationAdventureCardsDeck, LinkedList<ResourcesAdventureCard> resourcesAdventureCardsDeck, LinkedList<BeastCard> beastCardsDeck, LinkedList<MysteryCard> mysteryCardsDeck, LinkedList<DiscoveryToken> discoveryTokensStack, LinkedList<IslandTile> islandTilesStack, LinkedList<InventionCard> inventionCardsDeck) {
        this.eventCardsDeck = eventCardsDeck;
        this.buildingAdventureCardsDeck = buildingAdventureCardsDeck;
        this.explorationAdventureCardsDeck = explorationAdventureCardsDeck;
        this.resourcesAdventureCardsDeck = resourcesAdventureCardsDeck;
        this.beastCardsDeck = beastCardsDeck;
        this.mysteryCardsDeck = mysteryCardsDeck;
        this.discoveryTokensStack = discoveryTokensStack;
        this.islandTilesStack = islandTilesStack;
        this.inventionCardsDeck = inventionCardsDeck;
    }

    public LinkedList<EventCard> getEventCardsDeck() {
        return eventCardsDeck;
    }

    public void setEventCardsDeck(LinkedList<EventCard> eventCardsDeck) {
        this.eventCardsDeck = eventCardsDeck;
    }

    public LinkedList<BuildingAdventureCard> getBuildingAdventureCardsDeck() {
        return buildingAdventureCardsDeck;
    }

    public void setBuildingAdventureCardsDeck(LinkedList<BuildingAdventureCard> buildingAdventureCardsDeck) {
        this.buildingAdventureCardsDeck = buildingAdventureCardsDeck;
    }

    public LinkedList<ExplorationAdventureCard> getExplorationAdventureCardsDeck() {
        return explorationAdventureCardsDeck;
    }

    public void setExplorationAdventureCardsDeck(LinkedList<ExplorationAdventureCard> explorationAdventureCardsDeck) {
        this.explorationAdventureCardsDeck = explorationAdventureCardsDeck;
    }

    public LinkedList<ResourcesAdventureCard> getResourcesAdventureCardsDeck() {
        return resourcesAdventureCardsDeck;
    }

    public void setResourcesAdventureCardsDeck(LinkedList<ResourcesAdventureCard> resourcesAdventureCardsDeck) {
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

    public LinkedList<InventionCard> getInventionCardsDeck() {
        return inventionCardsDeck;
    }

    public void setInventionCardsDeck(LinkedList<InventionCard> inventionCardsDeck) {
        this.inventionCardsDeck = inventionCardsDeck;
    }
}
