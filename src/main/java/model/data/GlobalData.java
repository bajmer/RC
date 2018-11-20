package model.data;

import model.character.Character;
import model.decks.Decks;
import model.elements.cards.BeastCard;
import model.elements.cards.EventCard;
import model.elements.cards.InventionCard;
import model.elements.cards.StartingItemCard;
import model.elements.tiles.IslandTile;
import model.scenario.Scenario;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GlobalData {

    private Decks decks;
    private Scenario scenario;
    private List<Character> characters;
    private List<StartingItemCard> startingItems;
    private List<InventionCard> ideas;
    private List<InventionCard> inventions = new ArrayList<>();
    private List<IslandTile> discoveredIslandTiles = new ArrayList<>();
    private LinkedList<EventCard> threatActionCards = new LinkedList<>();
    private LinkedList<BeastCard> avaibleBeastCards = new LinkedList<>();

    public GlobalData(Decks decks, Scenario scenario, List<Character> characters, List<StartingItemCard> startingItems, List<InventionCard> ideas) {
        this.decks = decks;
        this.scenario = scenario;
        this.characters = characters;
        this.startingItems = startingItems;
        this.ideas = ideas;
    }
}
