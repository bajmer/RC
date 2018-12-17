package model.data;

import model.board.Board;
import model.cards.*;
import model.character.Character;
import model.character.MainCharacter;
import model.decks.Decks;
import model.elements.Dices;
import model.elements.Marker;
import model.elements.tiles.IslandTile;
import model.enums.TerrainType;
import model.resources.Resources;
import model.scenario.Scenario;

import java.util.*;

public class GlobalData {
    private static Decks decks;
    private static Scenario scenario;
    private static List<Character> characters;
    private static List<StartingItemCard> startingItems;
    private static List<IdeaCard> ideas;
    private static int mainCharactersNumber;
    private static Dices dices = new Dices();
    private static Board board = new Board();
    private static Levels levels = new Levels();
    private static List<IdeaCard> inventions = new ArrayList<>();
    private static List<MysteryCard> treasures = new ArrayList<>();
    private static List<IslandTile> discoveredIslandTiles = new ArrayList<>();
    private static Set<TerrainType> availableTerrainTypes = new HashSet<>();
    private static LinkedList<EventCard> threatActionCards = new LinkedList<>();
    private static LinkedList<BeastCard> availableBeastCards = new LinkedList<>();
    private static Resources availableResources = new Resources();
    private static Resources futureResources = new Resources();
    private static List<Marker> availableActionHelpers = new ArrayList<>();
    private static MainCharacter firstPlayer = null;
    private static IslandTile camp = null;
    private static boolean shelter = false;
    private static int firstPlayerModifier = -1;
    private static boolean unavailableInventions = false;

    public static Decks getDecks() {
        return decks;
    }

    public static void setDecks(Decks decks) {
        GlobalData.decks = decks;
    }

    public static Scenario getScenario() {
        return scenario;
    }

    public static void setScenario(Scenario scenario) {
        GlobalData.scenario = scenario;
    }

    public static List<Character> getCharacters() {
        return characters;
    }

    public static void setCharacters(List<Character> characters) {
        GlobalData.characters = characters;
    }

    public static List<StartingItemCard> getStartingItems() {
        return startingItems;
    }

    public static void setStartingItems(List<StartingItemCard> startingItems) {
        GlobalData.startingItems = startingItems;
    }

    public static List<IdeaCard> getIdeas() {
        return ideas;
    }

    public static void setIdeas(List<IdeaCard> ideas) {
        GlobalData.ideas = ideas;
    }

    public static int getMainCharactersNumber() {
        return mainCharactersNumber;
    }

    public static void setMainCharactersNumber(int mainCharactersNumber) {
        GlobalData.mainCharactersNumber = mainCharactersNumber;
    }

    public static Dices getDices() {
        return dices;
    }

    public static void setDices(Dices dices) {
        GlobalData.dices = dices;
    }

    public static Board getBoard() {
        return board;
    }

    public static void setBoard(Board board) {
        GlobalData.board = board;
    }

    public static Levels getLevels() {
        return levels;
    }

    public static void setLevels(Levels levels) {
        GlobalData.levels = levels;
    }

    public static List<IdeaCard> getInventions() {
        return inventions;
    }

    public static void setInventions(List<IdeaCard> inventions) {
        GlobalData.inventions = inventions;
    }

    public static List<MysteryCard> getTreasures() {
        return treasures;
    }

    public static void setTreasures(List<MysteryCard> treasures) {
        GlobalData.treasures = treasures;
    }

    public static List<IslandTile> getDiscoveredIslandTiles() {
        return discoveredIslandTiles;
    }

    public static void setDiscoveredIslandTiles(List<IslandTile> discoveredIslandTiles) {
        GlobalData.discoveredIslandTiles = discoveredIslandTiles;
    }

    public static Set<TerrainType> getAvailableTerrainTypes() {
        return availableTerrainTypes;
    }

    public static void setAvailableTerrainTypes(Set<TerrainType> availableTerrainTypes) {
        GlobalData.availableTerrainTypes = availableTerrainTypes;
    }

    public static LinkedList<EventCard> getThreatActionCards() {
        return threatActionCards;
    }

    public static void setThreatActionCards(LinkedList<EventCard> threatActionCards) {
        GlobalData.threatActionCards = threatActionCards;
    }

    public static LinkedList<BeastCard> getAvailableBeastCards() {
        return availableBeastCards;
    }

    public static void setAvailableBeastCards(LinkedList<BeastCard> availableBeastCards) {
        GlobalData.availableBeastCards = availableBeastCards;
    }

    public static Resources getAvailableResources() {
        return availableResources;
    }

    public static void setAvailableResources(Resources availableResources) {
        GlobalData.availableResources = availableResources;
    }

    public static Resources getFutureResources() {
        return futureResources;
    }

    public static void setFutureResources(Resources futureResources) {
        GlobalData.futureResources = futureResources;
    }

    public static List<Marker> getAvailableActionHelpers() {
        return availableActionHelpers;
    }

    public static void setAvailableActionHelpers(List<Marker> availableActionHelpers) {
        GlobalData.availableActionHelpers = availableActionHelpers;
    }

    public static MainCharacter getFirstPlayer() {
        return firstPlayer;
    }

    public static void setFirstPlayer(MainCharacter firstPlayer) {
        GlobalData.firstPlayer = firstPlayer;
    }

    public static IslandTile getCamp() {
        return camp;
    }

    public static void setCamp(IslandTile camp) {
        GlobalData.camp = camp;
    }

    public static boolean isShelter() {
        return shelter;
    }

    public static void setShelter(boolean shelter) {
        GlobalData.shelter = shelter;
    }

    public static int getFirstPlayerModifier() {
        return firstPlayerModifier;
    }

    public static void setFirstPlayerModifier(int firstPlayerModifier) {
        GlobalData.firstPlayerModifier = firstPlayerModifier;
    }

    public static boolean isUnavailableInventions() {
        return unavailableInventions;
    }

    public static void setUnavailableInventions(boolean unavailableInventions) {
        GlobalData.unavailableInventions = unavailableInventions;
    }
}
