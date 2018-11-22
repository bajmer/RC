package model.data;

import model.character.Character;
import model.character.main.MainCharacter;
import model.decks.Decks;
import model.elements.Dices;
import model.elements.cards.BeastCard;
import model.elements.cards.EventCard;
import model.elements.cards.IdeaCard;
import model.elements.cards.StartingItemCard;
import model.elements.tiles.IslandTile;
import model.enums.elements.ResourceType;
import model.resources.Resources;
import model.scenario.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class GlobalData {
    private Logger logger = LogManager.getLogger(GlobalData.class);

    private Decks decks;
    private Dices dices;
    private Scenario scenario;
    private List<Character> characters;
    private List<StartingItemCard> startingItems;
    private List<IdeaCard> ideas;
    private List<IdeaCard> inventions = new ArrayList<>();
    private Map<Integer, IslandTile> discoveredIslandTiles = new HashMap<>();
    private LinkedList<EventCard> threatActionCards = new LinkedList<>();
    private LinkedList<BeastCard> availableBeastCards = new LinkedList<>();
    private Resources availableResources = new Resources();
    private Resources futureResources = new Resources();
    private MainCharacter firstPlayer;
    private IslandTile camp;
    private boolean shelter = false;
    private int moraleLevel = 0;
    private int roofLevel = 0;
    private int palisadeLevel = 0;
    private int weaponLevel = 0;
    private int foodProduction = 0;
    private int woodProduction = 0;

    public GlobalData(Decks decks, Dices dices, Scenario scenario, List<Character> characters, List<StartingItemCard> startingItems, List<IdeaCard> ideas) {
        this.decks = decks;
        this.dices = dices;
        this.scenario = scenario;
        this.characters = characters;
        this.startingItems = startingItems;
        this.ideas = ideas;
    }

    private void reduceLivesLevelOfAllMainCharacters(int value) {
        logger.debug("Reducing the lives level of all main characters.");
        characters.stream()
                .filter(character -> character instanceof MainCharacter)
                .forEach(character -> character.changeLivesLevel(value));
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

    public void changeHideLevel(int value) {
        int beginHideAmount = availableResources.getHideAmount();
        int newHideAmount = beginHideAmount + value;
        if (newHideAmount < 0) {
            reduceLivesLevelOfAllMainCharacters(newHideAmount);
            newHideAmount = 0;
        }
        availableResources.setHideAmount(newHideAmount);
        if (newHideAmount != beginHideAmount) {
            logger.info("HIDE amount has been changed to: " + newHideAmount);
        }
    }

    public void changeMoraleLevel(int value) {
        final int MIN = -3;
        final int MAX = 3;

        int beginMoraleLevel = moraleLevel;
        int newMoraleLevel = beginMoraleLevel + value;
        moraleLevel = (newMoraleLevel > MAX) ? MAX : (moraleLevel < MIN) ? MIN : newMoraleLevel;
        if (moraleLevel != beginMoraleLevel) {
            logger.info("MORALE has been changed to level: " + moraleLevel);
        }
    }

    public void changeRoofLevel(int value) {
        int beginRoofLevel = roofLevel;
        int newRoofLevel = beginRoofLevel + value;
        if (newRoofLevel < 0) {
            reduceLivesLevelOfAllMainCharacters(newRoofLevel);
            newRoofLevel = 0;
        }
        roofLevel = newRoofLevel;
        if (roofLevel != beginRoofLevel) {
            logger.info("ROOF has been changed to level: " + roofLevel);
        }
    }

    public void changePalisadeLevel(int value) {
        int beginPalisadeLevel = palisadeLevel;
        int newPalisadeLevel = beginPalisadeLevel + value;
        if (newPalisadeLevel < 0) {
            reduceLivesLevelOfAllMainCharacters(newPalisadeLevel);
            newPalisadeLevel = 0;
        }
        palisadeLevel = newPalisadeLevel;
        if (palisadeLevel != beginPalisadeLevel) {
            logger.info("PALISADE has been changed to level: " + palisadeLevel);
        }
    }

    public void changeWeaponLevel(int value) {
        int beginWeaponLevel = weaponLevel;
        int newWeaponLevel = beginWeaponLevel + value;
        if (newWeaponLevel < 0) {
            reduceLivesLevelOfAllMainCharacters(newWeaponLevel);
            newWeaponLevel = 0;
        }
        weaponLevel = newWeaponLevel;
        if (weaponLevel != beginWeaponLevel) {
            logger.info("WEAPON has been changed to level: " + weaponLevel);
        }
    }

    public void discoveredNewIslandTile(IslandTile islandTile) {
        // TODO: 2018-11-22 Handle terrain type

        // TODO: 2018-11-22 Handle resources

        // TODO: 2018-11-22 Handle totems

        // TODO: 2018-11-22 Handle discovery tokens

    }

    public void movingShelterToNewTile(IslandTile targetIslandTile) {
        // TODO: 2018-11-22 Setting wood and food production
        List<ResourceType> resources = new ArrayList<>();
        resources.add(targetIslandTile.getLeftSquareResource());
        resources.add(targetIslandTile.getRightSquareResource());
        foodProduction = Math.toIntExact(resources.stream().filter(resourceType -> resourceType == ResourceType.FOOD).count());
        woodProduction = Math.toIntExact(resources.stream().filter(resourceType -> resourceType == ResourceType.WOOD).count());

        // TODO: 2018-11-22 Chcecking production modifications, etc.

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

    public Map<Integer, IslandTile> getDiscoveredIslandTiles() {
        return discoveredIslandTiles;
    }

    public void setDiscoveredIslandTiles(Map<Integer, IslandTile> discoveredIslandTiles) {
        this.discoveredIslandTiles = discoveredIslandTiles;
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

    public boolean isShelter() {
        return shelter;
    }

    public void setShelter(boolean shelter) {
        this.shelter = shelter;
    }

    public int getMoraleLevel() {
        return moraleLevel;
    }

    public void setMoraleLevel(int moraleLevel) {
        this.moraleLevel = moraleLevel;
    }

    public int getRoofLevel() {
        return roofLevel;
    }

    public void setRoofLevel(int roofLevel) {
        this.roofLevel = roofLevel;
    }

    public int getPalisadeLevel() {
        return palisadeLevel;
    }

    public void setPalisadeLevel(int palisadeLevel) {
        this.palisadeLevel = palisadeLevel;
    }

    public int getWeaponLevel() {
        return weaponLevel;
    }

    public void setWeaponLevel(int weaponLevel) {
        this.weaponLevel = weaponLevel;
    }

    public int getFoodProduction() {
        return foodProduction;
    }

    public void setFoodProduction(int foodProduction) {
        this.foodProduction = foodProduction;
    }

    public int getWoodProduction() {
        return woodProduction;
    }

    public void setWoodProduction(int woodProduction) {
        this.woodProduction = woodProduction;
    }
}
