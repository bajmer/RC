package model.data;

import model.character.Character;
import model.decks.Decks;
import model.elements.decks.cards.InventionCard;
import model.elements.decks.cards.StartingItemCard;
import model.elements.decks.tiles.IslandTile;
import model.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    private Decks decks;
    private Scenario scenario;
    private List<Character> characters;
    private List<StartingItemCard> startingItems;
    private List<InventionCard> ideas;
    private List<InventionCard> inventions = new ArrayList<>();
    private List<IslandTile> discoveredIslandTiles = new ArrayList<>();

    public GlobalData(Decks decks, Scenario scenario, List<Character> characters, List<StartingItemCard> startingItems, List<InventionCard> ideas) {
        this.decks = decks;
        this.scenario = scenario;
        this.characters = characters;
        this.startingItems = startingItems;
        this.ideas = ideas;
    }
}
