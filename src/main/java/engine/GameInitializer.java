package engine;

import model.character.Character;
import model.character.additional.FridayCharacter;
import model.character.main.MainCharacter;
import model.data.GlobalData;
import model.decks.Decks;
import model.elements.decks.cards.BeastCard;
import model.elements.decks.cards.EventCard;
import model.elements.decks.cards.InventionCard;
import model.elements.decks.cards.StartingItemCard;
import model.elements.decks.cards.adventure.BuildingAdventureCard;
import model.elements.decks.cards.adventure.ExplorationAdventureCard;
import model.elements.decks.cards.adventure.ResourcesAdventureCard;
import model.elements.decks.cards.mystery.MysteryCard;
import model.elements.decks.cards.mystery.MysteryMonsterCard;
import model.elements.decks.cards.mystery.MysteryTrapCard;
import model.elements.decks.cards.mystery.MysteryTreasureCard;
import model.elements.decks.tiles.DiscoveryToken;
import model.elements.decks.tiles.IslandTile;
import model.enums.*;
import model.enums.cards.BeastType;
import model.enums.cards.StartingItemType;
import model.enums.cards.adventure.BuildingAdventureType;
import model.enums.cards.adventure.ExplorationAdventureType;
import model.enums.cards.adventure.ResourcesAdventureType;
import model.enums.cards.event.EventEffectType;
import model.enums.cards.event.EventIconType;
import model.enums.cards.event.ThreatActionType;
import model.enums.cards.event.ThreatEffectType;
import model.enums.cards.mystery.MysteryMonsterType;
import model.enums.cards.mystery.MysteryTrapType;
import model.enums.cards.mystery.MysteryTreasureType;
import model.enums.elements.ResourceType;
import model.options.Options;
import model.scenario.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigReader;

import java.util.*;

import static model.enums.cards.event.EventIconType.*;

class GameInitializer {
    private final String SCENARIO_PREFIX = "SCENARIO";
    private final String CHARACTER_PREFIX = "CHARACTER";
    private final String EVENT_PREFIX = "EVENT";
    private final String ADV_BUILDING_PREFIX = "ADV_BUILDING";
    private final String ADV_EXPLORATION_PREFIX = "ADV_EXPLORATION";
    private final String ADV_RESOURCES_PREFIX = "ADV_RESOURCES";
    private final String BEAST_PREFIX = "BEAST";
    private final String ISLAND_PREFIX = "ISLAND";
    private Logger logger = LogManager.getLogger(GameInitializer.class);
    private Options options;

    GameInitializer(Options options) {
        this.options = options;
    }

    public Options getOptions() {
        return options;
    }

    GlobalData initialize() {

//		creating scenario, characters, startingItems, etc.
        Scenario scenario = createScenario();
        List<Character> characters = createCharacters();
        List<StartingItemCard> startingItems = createStartingItems();

//		creating all stacks
        LinkedList<EventCard> eventsDeck = createEventsDeck(scenario.getAllRounds());
        LinkedList<BuildingAdventureCard> buildingAdventuresDeck = createBuildingAdventuresDeck();
        LinkedList<ExplorationAdventureCard> explorationAdventuresDeck = createExplorationAdventuresDeck();
        LinkedList<ResourcesAdventureCard> resourcesAdventuresDeck = createResourcesAdventuresDeck();
        LinkedList<BeastCard> beastsDeck = createBeastsDeck();
        LinkedList<MysteryCard> mysteriesDeck = createMysteriesDeck();
        LinkedList<DiscoveryToken> discoveriesStack = createDiscoveriesStack();
        LinkedList<IslandTile> islandTilesStack = createIslandTilesStack();
        LinkedList<InventionCard> inventionsDeck = createInventionsDeck();

        List<InventionCard> ideas = chooseInventionCards();

        Decks decks = new Decks(
                eventsDeck,
                buildingAdventuresDeck,
                explorationAdventuresDeck,
                resourcesAdventuresDeck,
                beastsDeck,
                mysteriesDeck,
                discoveriesStack,
                islandTilesStack,
                inventionsDeck
        );

        return new GlobalData(decks, scenario, characters, startingItems, ideas);
    }

    private Scenario createScenario() {
        int scenarioID = options.getScenarioNumber();
        Scenario scenario = new Scenario(
                scenarioID,
                Integer.parseInt(ConfigReader.loadValue(SCENARIO_PREFIX, Integer.toString(scenarioID), "ALL_ROUNDS"))
        );
        logger.info("Utworzono scenariusz.");

        return scenario;
    }

    private List<Character> createCharacters() {
        List<Character> characters = new ArrayList<>();
        options.getCharacters().forEach(choosedCharacter -> {
            ProfessionType profession = choosedCharacter.getProfession();
            SexType sex = choosedCharacter.getSex();
            characters.add(new MainCharacter(
                    profession,
                    sex,
                    new ArrayList<>(Arrays.asList(
                            SpecialSkillType.valueOf(ConfigReader.loadValue(CHARACTER_PREFIX, profession.toString(), "SKILL_1")),
                            SpecialSkillType.valueOf(ConfigReader.loadValue(CHARACTER_PREFIX, profession.toString(), "SKILL_2")),
                            SpecialSkillType.valueOf(ConfigReader.loadValue(CHARACTER_PREFIX, profession.toString(), "SKILL_3")),
                            SpecialSkillType.valueOf(ConfigReader.loadValue(CHARACTER_PREFIX, profession.toString(), "SKILL_4"))
                    )),
                    Integer.parseInt(ConfigReader.loadValue(CHARACTER_PREFIX, profession.toString(), "LIVES"))
            ));
        });

        if (options.isActiveFriday()) {
            characters.add(new FridayCharacter());
        }

        logger.info("Utworzono postacie.");
        return characters;
    }

    private List<StartingItemCard> createStartingItems() {

        LinkedList<StartingItemCard> startingItemsStack = new LinkedList<>();
        Arrays.asList(StartingItemType.values()).forEach(startingItemType -> startingItemsStack.add(new StartingItemCard(startingItemType)));
        Collections.shuffle(startingItemsStack);

        List<StartingItemCard> startingItems = new ArrayList<>();
        int startingItemsNumber = options.getStartingItemsNumber();

        for (int i = 0; i < startingItemsNumber; i++) {
            startingItems.add(startingItemsStack.removeFirst());
        }

        logger.info("Wylosowano przedmioty startowe.");
        for (StartingItemCard card : startingItems) {
            logger.debug(card);
        }

        return startingItems;
    }

    private LinkedList<EventCard> createEventsDeck(int scenarioAllRounds) {
        LinkedList<EventCard> allEventsDeck = new LinkedList<>();
        Arrays.stream(EventEffectType.values()).filter(eventEffectType -> !eventEffectType.toString().startsWith("ADV_")).forEach(eventEffectType -> allEventsDeck.add(new EventCard(
                eventEffectType,
                EventIconType.valueOf(ConfigReader.loadValue(EVENT_PREFIX, eventEffectType.toString(), "EVENT_ICON")),
                ThreatActionType.valueOf(ConfigReader.loadValue(EVENT_PREFIX, eventEffectType.toString(), "THREAT_ACTION")),
                ThreatEffectType.valueOf(ConfigReader.loadValue(EVENT_PREFIX, eventEffectType.toString(), "THREAT_EFFECT"))
        )));
        Collections.shuffle(allEventsDeck);

        int bookIconsCounter = 0;
        int questionmarkIconsCouter = 0;
        LinkedList<EventCard> eventsDeck = new LinkedList<>();

        boolean wreckageEventAdded = false;
        for (EventCard card : allEventsDeck) {
            if (Arrays.asList("FOOD_CRATES", "WRECKED_LIFEBOAT", "CAPTAINS_CHEST").contains(card.getEventEffect().toString())) {
                if (!wreckageEventAdded) {
                    eventsDeck.addFirst(card);
                    wreckageEventAdded = true;
                }
                continue;
            }
            if (card.getEventIcon() == BOOK && bookIconsCounter < scenarioAllRounds / 2) {
                eventsDeck.add(card);
                bookIconsCounter++;
                continue;
            }
            if ((card.getEventIcon() == BUILDING_ADVENTURE || card.getEventIcon() == RESOURCES_ADVENTURE || card.getEventIcon() == EXPLORATION_ADVENTURE) &&
                    questionmarkIconsCouter < scenarioAllRounds / 2) {
                eventsDeck.add(card);
                questionmarkIconsCouter++;
            }
        }

        logger.info("Przygotowano talię wydarzeń.");
        return eventsDeck;
    }

    private LinkedList<BuildingAdventureCard> createBuildingAdventuresDeck() {
        LinkedList<BuildingAdventureCard> buildingAdventuresDeck = new LinkedList<>();
        Arrays.asList(BuildingAdventureType.values()).forEach(buildingAdventureType -> buildingAdventuresDeck.add(
                new BuildingAdventureCard(
                        buildingAdventureType,
                        EventEffectType.valueOf(ConfigReader.loadValue(ADV_BUILDING_PREFIX, buildingAdventureType.toString(), "EVENT_NAME"))
                )
        ));
        Collections.shuffle(buildingAdventuresDeck);

        logger.info("Przygotowano talię przygód budowy.");
        return buildingAdventuresDeck;
    }

    private LinkedList<ExplorationAdventureCard> createExplorationAdventuresDeck() {
        LinkedList<ExplorationAdventureCard> explorationAdventuresDeck = new LinkedList<>();
        Arrays.asList(ExplorationAdventureType.values()).forEach(explorationAdventureType -> explorationAdventuresDeck.add(
                new ExplorationAdventureCard(
                        explorationAdventureType,
                        EventEffectType.valueOf(ConfigReader.loadValue(ADV_EXPLORATION_PREFIX, explorationAdventureType.toString(), "EVENT_NAME"))
                )
        ));
        Collections.shuffle(explorationAdventuresDeck);

        logger.info("Przygotowano talię przygód eksploracji.");
        return explorationAdventuresDeck;
    }

    private LinkedList<ResourcesAdventureCard> createResourcesAdventuresDeck() {
        LinkedList<ResourcesAdventureCard> resourcesAdventuresDeck = new LinkedList<>();
        Arrays.asList(ResourcesAdventureType.values()).forEach(resourcesAdventureType -> resourcesAdventuresDeck.add(
                new ResourcesAdventureCard(
                        resourcesAdventureType,
                        EventEffectType.valueOf(ConfigReader.loadValue(ADV_RESOURCES_PREFIX, resourcesAdventureType.toString(), "EVENT_NAME"))
                )
        ));
        Collections.shuffle(resourcesAdventuresDeck);

        logger.info("Przygotowano talię przygód zbierania surowców.");
        return resourcesAdventuresDeck;

    }

    private LinkedList<BeastCard> createBeastsDeck() {
        LinkedList<BeastCard> beastsDeck = new LinkedList<>();
        Arrays.asList(BeastType.values()).forEach(beastType -> beastsDeck.add(new BeastCard(
                beastType,
                Integer.parseInt(ConfigReader.loadValue(BEAST_PREFIX, beastType.toString(), "STRENGTH")),
                Integer.parseInt(ConfigReader.loadValue(BEAST_PREFIX, beastType.toString(), "DAMAGE")),
                Integer.parseInt(ConfigReader.loadValue(BEAST_PREFIX, beastType.toString(), "FOOD")),
                Integer.parseInt(ConfigReader.loadValue(BEAST_PREFIX, beastType.toString(), "FURS"))
        )));
        Collections.shuffle(beastsDeck);

        logger.info("Przygotowano talię bestii.");
        return beastsDeck;
    }

    private LinkedList<MysteryCard> createMysteriesDeck() {
        LinkedList<MysteryCard> mysteriesDeck = new LinkedList<>();
        Arrays.asList(MysteryTreasureType.values()).forEach(mysteryTreasureType -> mysteriesDeck.add(new MysteryTreasureCard(mysteryTreasureType)));
        Arrays.asList(MysteryMonsterType.values()).forEach(mysteryMonsterType -> mysteriesDeck.add(new MysteryMonsterCard(mysteryMonsterType)));
        Arrays.asList(MysteryTrapType.values()).forEach(mysteryTrapType -> mysteriesDeck.add(new MysteryTrapCard(mysteryTrapType)));
        Collections.shuffle(mysteriesDeck);

        logger.info("Przygotowano talię tajemnic.");
        return mysteriesDeck;
    }

    private LinkedList<DiscoveryToken> createDiscoveriesStack() {
        LinkedList<DiscoveryToken> discoveryTokensDeck = new LinkedList<>();
        Arrays.asList(DiscoveryTokenType.values()).forEach(discoveryToken -> discoveryTokensDeck.add(
                new DiscoveryToken(discoveryToken)));
        Collections.shuffle(discoveryTokensDeck);

        logger.info("Utworzono stos żetonów odkryć.");
        return discoveryTokensDeck;
    }

    private LinkedList<IslandTile> createIslandTilesStack() {
        LinkedList<IslandTile> islandTilesDeck = new LinkedList<>();
        for (int i = 1; i <= 11; i++) {
            IslandTile islandTile = new IslandTile(
                    i,
                    TerrainType.valueOf(ConfigReader.loadValue(ISLAND_PREFIX, String.valueOf(i), "TERRAIN")),
                    ResourceType.valueOf(ConfigReader.loadValue(ISLAND_PREFIX, String.valueOf(i), "LEFT_RESOURCE")),
                    ResourceType.valueOf(ConfigReader.loadValue(ISLAND_PREFIX, String.valueOf(i), "RIGHT_RESOURCE")),
                    Integer.parseInt(ConfigReader.loadValue(ISLAND_PREFIX, String.valueOf(i), "TOTEMS")),
                    Integer.parseInt(ConfigReader.loadValue(ISLAND_PREFIX, String.valueOf(i), "TOKENS")),
                    Boolean.parseBoolean(ConfigReader.loadValue(ISLAND_PREFIX, String.valueOf(i), "NATURAL_SHELTER"))
            );

            if (i != 8) {
                islandTilesDeck.add(islandTile);
            } else {
                // TODO: 2018-11-16
//				GameInfo.getDiscoveredTiles().add(islandTile);
//				board.getTilePositionIdToIslandTile().put(1, islandTile);
//				GameInfo.setCamp(islandTile);
            }
        }
        Collections.shuffle(islandTilesDeck);

        logger.info("Przygotowano stos kafelków wyspy.");
        return islandTilesDeck;
    }

    private LinkedList<InventionCard> createInventionsDeck() {

        return null;
    }

    private List<InventionCard> chooseInventionCards() {

        return null;
    }

    //
    //createActions();
    //createCharacterMarkers();
}
