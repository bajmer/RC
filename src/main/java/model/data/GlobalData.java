package model.data;

import model.board.Board;
import model.character.Character;
import model.character.main.MainCharacter;
import model.decks.Decks;
import model.elements.Dices;
import model.elements.Marker;
import model.elements.cards.*;
import model.elements.tiles.IslandTile;
import model.enums.TerrainType;
import model.enums.elements.ResourceType;
import model.resources.Resources;
import model.scenario.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GlobalData {
    private Logger logger = LogManager.getLogger(GlobalData.class);

    private Decks decks;
    private Dices dices;
    private Board board;
    private Scenario scenario;
    private List<Character> characters;
    private List<StartingItemCard> startingItems;
    private List<IdeaCard> ideas;
    private List<IdeaCard> inventions = new ArrayList<>();
    private List<MysteryCard> treasures = new ArrayList<>();
    private List<IslandTile> discoveredIslandTiles = new ArrayList<>();
    private List<TerrainType> availableTerrainTypes = new ArrayList<>();
    private LinkedList<EventCard> threatActionCards = new LinkedList<>();
    private LinkedList<BeastCard> availableBeastCards = new LinkedList<>();
    private Resources availableResources = new Resources();
    private Resources futureResources = new Resources();
    private MainCharacter firstPlayer;
    private IslandTile camp;
    private List<Marker> availableActionHelpers = new ArrayList<>();
    private GameParams gameParams = new GameParams();
    private boolean shelter = false;

    public GlobalData(Decks decks, Dices dices, Board board, Scenario scenario, List<Character> characters, List<StartingItemCard> startingItems, List<IdeaCard> ideas) {
        this.decks = decks;
        this.dices = dices;
        this.board = board;
        this.scenario = scenario;
        this.characters = characters;
        this.startingItems = startingItems;
        this.ideas = ideas;
    }

    public void reduceLivesLevelOfAllMainCharacters(int value) {
        logger.debug("Reducing the lives level of all main characters.");
        characters.stream()
                .filter(character -> character instanceof MainCharacter)
                .forEach(character -> {
                    character.changeLivesLevel(value);
                    decreaseMoraleAfterReducingCharacterLives(value, (MainCharacter) character);
                });
    }

    public void decreaseMoraleAfterReducingCharacterLives(int value, MainCharacter character) {
        if (value < 0) {
            int lives = character.getLives();
            List<Integer> moraleDown = character.getMoraleDown();
            for (int i = lives; i >= lives - value; i++) {
                if (moraleDown.contains(i)) {
                    changeMoraleLevel(-1);
                }
            }
        }
    }

    public void changeFoodLevel(int value) {
        int beginFoodAmount = availableResources.getFoodAmount();
        int newFoodAmount = beginFoodAmount + value;
        if (newFoodAmount < 0) {
            changeLongTermFoodLevel(newFoodAmount);
            newFoodAmount = 0;
        }
        availableResources.setFoodAmount(newFoodAmount);
        if (newFoodAmount != beginFoodAmount) {
            logger.info("FOOD amount has been changed to: " + newFoodAmount);
        }
    }

    public void changeLongTermFoodLevel(int value) {
        int beginLongTermFoodAmount = availableResources.getLongTermFoodAmount();
        int newLongTermFoodAmount = beginLongTermFoodAmount + value;
        if (newLongTermFoodAmount < 0) {
            reduceLivesLevelOfAllMainCharacters(newLongTermFoodAmount);
            newLongTermFoodAmount = 0;
        }
        availableResources.setLongTermFoodAmount(newLongTermFoodAmount);
        if (newLongTermFoodAmount != beginLongTermFoodAmount) {
            logger.info("LONG-TERM FOOD amount has been changed to: " + newLongTermFoodAmount);
        }

    }

    public void changeWoodLevel(int value) {
        int beginWoodAmount = availableResources.getWoodAmount();
        int newWoodAmount = beginWoodAmount + value;
        if (newWoodAmount < 0) {
            reduceLivesLevelOfAllMainCharacters(newWoodAmount);
            newWoodAmount = 0;
        }
        availableResources.setWoodAmount(newWoodAmount);
        if (newWoodAmount != beginWoodAmount) {
            logger.info("WOOD amount has been changed to: " + newWoodAmount);
        }
    }

    public void changeFurLevel(int value) {
        int beginFurAmount = availableResources.getFurAmount();
        int newFurAmount = beginFurAmount + value;
        if (newFurAmount < 0) {
            reduceLivesLevelOfAllMainCharacters(newFurAmount);
            newFurAmount = 0;
        }
        availableResources.setFurAmount(newFurAmount);
        if (newFurAmount != beginFurAmount) {
            logger.info("FURS amount has been changed to: " + newFurAmount);
        }
    }

    public void changeMoraleLevel(int value) {
        final int MIN = -3;
        final int MAX = 3;

        int beginMoraleLevel = gameParams.getMoraleLevel();
        int newMoraleLevel = beginMoraleLevel + value;
        newMoraleLevel = (newMoraleLevel > MAX) ? MAX : (newMoraleLevel < MIN) ? MIN : newMoraleLevel;
        gameParams.setMoraleLevel(newMoraleLevel);
        if (newMoraleLevel != beginMoraleLevel) {
            logger.info("MORALE has been changed to level: " + newMoraleLevel);
        }
    }

    public void changeRoofLevel(int value) {
        int beginRoofLevel = gameParams.getRoofLevel();
        int newRoofLevel = beginRoofLevel + value;
        if (newRoofLevel < 0) {
            reduceLivesLevelOfAllMainCharacters(newRoofLevel);
            newRoofLevel = 0;
        }
        gameParams.setRoofLevel(newRoofLevel);
        if (newRoofLevel != beginRoofLevel) {
            logger.info("ROOF has been changed to level: " + newRoofLevel);
        }
    }

    public void changePalisadeLevel(int value) {
        int beginPalisadeLevel = gameParams.getPalisadeLevel();
        int newPalisadeLevel = beginPalisadeLevel + value;
        if (newPalisadeLevel < 0) {
            reduceLivesLevelOfAllMainCharacters(newPalisadeLevel);
            newPalisadeLevel = 0;
        }
        gameParams.setPalisadeLevel(newPalisadeLevel);
        if (newPalisadeLevel != beginPalisadeLevel) {
            logger.info("PALISADE has been changed to level: " + newPalisadeLevel);
        }
    }

    public void changeWeaponLevel(int value, Character character) {
        int beginWeaponLevel = gameParams.getWeaponLevel();
        int newWeaponLevel = beginWeaponLevel + value;
        if (newWeaponLevel < 0) {
            if (character != null) {
                character.changeLivesLevel(newWeaponLevel);
            } else {
                reduceLivesLevelOfAllMainCharacters(newWeaponLevel);
            }
            newWeaponLevel = 0;
        }
        gameParams.setWeaponLevel(newWeaponLevel);
        if (newWeaponLevel != beginWeaponLevel) {
            logger.info("WEAPON has been changed to level: " + newWeaponLevel);
        }
    }

    public void handleNewIslandTileDiscovery(IslandTile islandTile) {
        // TODO: 2018-11-22 Handle terrain type

        // TODO: 2018-11-22 Handle resources

        // TODO: 2018-11-22 Handle totems

        // TODO: 2018-11-22 Handle discovery tokens

    }

    public void handleMovingShelterToNewTile(IslandTile targetIslandTile) {
        // TODO: 2018-11-22 Setting wood and food production
        List<ResourceType> resources = new ArrayList<>();
        resources.add(targetIslandTile.getLeftSquareResource());
        resources.add(targetIslandTile.getRightSquareResource());
        int newFoodProduction = Math.toIntExact(resources.stream().filter(resourceType -> resourceType == ResourceType.FOOD).count());
        int newWoodProduction = Math.toIntExact(resources.stream().filter(resourceType -> resourceType == ResourceType.WOOD).count());
        gameParams.setFoodProduction(newFoodProduction);
        gameParams.setWoodProduction(newWoodProduction);
        // TODO: 2018-11-22 Checking production modifications, etc.

        // TODO: 2018-11-22 Handle natural shelter or not

        // TODO: 2018-11-22 Handle moving tokens

        // TODO: 2018-11-22 Handle moving built shelter, roof, etc.

        camp = targetIslandTile;
    }

    public Decks getDecks() {
        return decks;
    }

    public void setDecks(Decks decks) {
        this.decks = decks;
    }

    public Dices getDices() {
        return dices;
    }

    public void setDices(Dices dices) {
        this.dices = dices;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public List<StartingItemCard> getStartingItems() {
        return startingItems;
    }

    public void setStartingItems(List<StartingItemCard> startingItems) {
        this.startingItems = startingItems;
    }

    public List<IdeaCard> getIdeas() {
        return ideas;
    }

    public void setIdeas(List<IdeaCard> ideas) {
        this.ideas = ideas;
    }

    public List<IdeaCard> getInventions() {
        return inventions;
    }

    public void setInventions(List<IdeaCard> inventions) {
        this.inventions = inventions;
    }

    public List<MysteryCard> getTreasures() {
        return treasures;
    }

    public void setTreasures(List<MysteryCard> treasures) {
        this.treasures = treasures;
    }

    public List<IslandTile> getDiscoveredIslandTiles() {
        return discoveredIslandTiles;
    }

    public void setDiscoveredIslandTiles(List<IslandTile> discoveredIslandTiles) {
        this.discoveredIslandTiles = discoveredIslandTiles;
    }

    public List<TerrainType> getAvailableTerrainTypes() {
        return availableTerrainTypes;
    }

    public void setAvailableTerrainTypes(List<TerrainType> availableTerrainTypes) {
        this.availableTerrainTypes = availableTerrainTypes;
    }

    public LinkedList<EventCard> getThreatActionCards() {
        return threatActionCards;
    }

    public void setThreatActionCards(LinkedList<EventCard> threatActionCards) {
        this.threatActionCards = threatActionCards;
    }

    public LinkedList<BeastCard> getAvailableBeastCards() {
        return availableBeastCards;
    }

    public void setAvailableBeastCards(LinkedList<BeastCard> availableBeastCards) {
        this.availableBeastCards = availableBeastCards;
    }

    public Resources getAvailableResources() {
        return availableResources;
    }

    public void setAvailableResources(Resources availableResources) {
        this.availableResources = availableResources;
    }

    public Resources getFutureResources() {
        return futureResources;
    }

    public void setFutureResources(Resources futureResources) {
        this.futureResources = futureResources;
    }

    public MainCharacter getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(MainCharacter firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public IslandTile getCamp() {
        return camp;
    }

    public void setCamp(IslandTile camp) {
        this.camp = camp;
    }

    public List<Marker> getAvailableActionHelpers() {
        return availableActionHelpers;
    }

    public void setAvailableActionHelpers(List<Marker> availableActionHelpers) {
        this.availableActionHelpers = availableActionHelpers;
    }

    public GameParams getGameParams() {
        return gameParams;
    }

    public void setGameParams(GameParams gameParams) {
        this.gameParams = gameParams;
    }

    public boolean isShelter() {
        return shelter;
    }

    public void setShelter(boolean shelter) {
        this.shelter = shelter;
    }
}
