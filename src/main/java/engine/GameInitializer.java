package engine;

import engine.utils.ConfigReader;
import model.cards.*;
import model.character.Character;
import model.character.FridayCharacter;
import model.character.MainCharacter;
import model.data.GlobalData;
import model.decks.Decks;
import model.elements.tiles.DiscoveryToken;
import model.elements.tiles.IslandTile;
import model.enums.*;
import model.enums.cards.*;
import model.enums.cards.event.EventType;
import model.enums.cards.event.IconType;
import model.enums.cards.event.ThreatType;
import model.enums.elements.ResourceType;
import model.options.Options;
import model.scenario.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static model.enums.cards.event.IconType.*;

class GameInitializer {
    private final String IDEA_PREFIX = "IDEA";

    private final String SCENARIO_PREFIX = "SCENARIO";
    private final String CHARACTER_PREFIX = "CHARACTER";
    private final String EVENT_PREFIX = "EVENT";
    private final String ADVENTURE_PREFIX = "ADVENTURE";
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

    void initialize() {

//		creating scenario, characters, startingItems, etc.
        Scenario scenario = scenarioInitialization();
        List<Character> characters = charactersInitialization();

//		creating all stacks
        LinkedList<EventCard> eventsDeck = eventsDeckInitialization(scenario.getAllRounds());
        LinkedList<AdventureCard> buildingAdventuresDeck = buildingAdventuresDeckInitialization();
        LinkedList<AdventureCard> explorationAdventuresDeck = explorationAdventuresDeckInitialization();
        LinkedList<AdventureCard> resourcesAdventuresDeck = resourcesAdventuresDeckInitialization();
        LinkedList<BeastCard> beastsDeck = beastsDeckInitialization();
        LinkedList<MysteryCard> mysteriesDeck = mysteriesDeckInitialization();
        LinkedList<DiscoveryToken> discoveriesStack = discoveriesStackInitialization();
        LinkedList<IslandTile> islandTilesStack = islandTilesStackInitialization();
        LinkedList<IdeaCard> inventionsDeck = ideasDeckInitialization();
        LinkedList<StartingItemCard> startingItemsDeck = startingItemsInitialization();

        List<IdeaCard> ideas = drawInitialIdeas(inventionsDeck, scenario.getId());
        List<StartingItemCard> startingItems = drawStartingItems(startingItemsDeck);

        Decks decks = new Decks(
                eventsDeck,
                buildingAdventuresDeck,
                explorationAdventuresDeck,
                resourcesAdventuresDeck,
                beastsDeck,
                mysteriesDeck,
                discoveriesStack,
                islandTilesStack,
                inventionsDeck,
                startingItemsDeck
        );

        GlobalData.setDecks(decks);
        GlobalData.setScenario(scenario);
        GlobalData.setCharacters(characters);
        GlobalData.setStartingItems(startingItems);
        GlobalData.setIdeas(ideas);
        GlobalData.setMainCharactersNumber(Math.toIntExact(characters.stream().filter(character -> character instanceof MainCharacter).count()));
    }

    private Scenario scenarioInitialization() {
        int scenarioID = options.getScenarioNumber();
        Scenario scenario = new Scenario(
                scenarioID,
                ConfigReader.loadValue(Integer.class, SCENARIO_PREFIX, Integer.toString(scenarioID), "ALL_ROUNDS")
        );
        logger.info("SCENARIO has been initialized.");

        return scenario;
    }

    private List<Character> charactersInitialization() {
        List<Character> characters = new ArrayList<>();
        options.getCharacters().forEach(chosenCharacter -> {
            ProfessionType profession = chosenCharacter.getProfession();
            SexType sex = chosenCharacter.getSex();
            String moraleDownString = ConfigReader.loadValue(String.class, CHARACTER_PREFIX, profession.toString(), "MORALE_DOWN");
            List<String> moraleDownStringArray = Arrays.asList(moraleDownString.split("-"));
            List<Integer> moraleDownIntArray = new ArrayList<>();
            moraleDownStringArray.forEach(s -> moraleDownIntArray.add(Integer.parseInt(s)));
            characters.add(new MainCharacter(
                    profession,
                    sex,
                    Arrays.asList(
                            ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_1"),
                            ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_2"),
                            ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_3"),
                            ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_4"),
                            ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_4")
                    ),
                    moraleDownIntArray,
                    ConfigReader.loadValue(Integer.class, CHARACTER_PREFIX, profession.toString(), "LIVES")
            ));
        });

        if (options.isActiveFriday()) {
            characters.add(new FridayCharacter());
        }

        logger.info("CHARACTERS has been initialized.");
        return characters;
    }

    private LinkedList<EventCard> eventsDeckInitialization(int scenarioAllRounds) {
        LinkedList<EventCard> allEventsDeck = new LinkedList<>();
        Arrays.stream(EventType.values())
                .filter(eventType -> eventType.toString().startsWith("EVENT_"))
                .forEach(eventType -> allEventsDeck.add(new EventCard(
                        eventType,
                        ConfigReader.loadValue(IconType.class, EVENT_PREFIX, eventType.toString(), "ICON"),
                        ConfigReader.loadValue(ThreatType.class, EVENT_PREFIX, eventType.toString(), "THREAT")
                )));
        Collections.shuffle(allEventsDeck);

        int bookIconsCounter = 0;
        int questionMarkIconsCounter = 0;
        LinkedList<EventCard> eventsDeck = new LinkedList<>();

        boolean wreckageEventAdded = false;
        for (EventCard card : allEventsDeck) {
            if (Arrays.asList("FOOD_CRATES", "WRECKED_LIFEBOAT", "CAPTAINS_CHEST").contains(card.getEvent().toString())) {
                if (!wreckageEventAdded) {
                    eventsDeck.addFirst(card);
                    wreckageEventAdded = true;
                }
                continue;
            }
            if (card.getIcon() == BOOK && bookIconsCounter < scenarioAllRounds / 2) {
                eventsDeck.add(card);
                bookIconsCounter++;
                continue;
            }
            if ((card.getIcon() == BUILDING_ADVENTURE || card.getIcon() == RESOURCES_ADVENTURE || card.getIcon() == EXPLORATION_ADVENTURE) &&
                    questionMarkIconsCounter < scenarioAllRounds / 2) {
                eventsDeck.add(card);
                questionMarkIconsCounter++;
            }
        }

        logger.info("EVENTS DECK has been initialized.");
        return eventsDeck;
    }

    private LinkedList<AdventureCard> buildingAdventuresDeckInitialization() {
        LinkedList<AdventureCard> buildingAdventuresDeck = new LinkedList<>();
        Arrays.stream(AdventureType.values())
                .filter(adventureType -> adventureType.toString().startsWith("BUILD_"))
                .forEach(adventureType -> buildingAdventuresDeck.add(new AdventureCard(
                                adventureType,
                                ConfigReader.loadValue(EventType.class, ADVENTURE_PREFIX, adventureType.toString(), "EVENT")
                        )
                ));
        Collections.shuffle(buildingAdventuresDeck);

        logger.info("BUILDING ADVENTURES DECK has been initialized.");
        return buildingAdventuresDeck;
    }

    private LinkedList<AdventureCard> explorationAdventuresDeckInitialization() {
        LinkedList<AdventureCard> explorationAdventuresDeck = new LinkedList<>();
        Arrays.stream(AdventureType.values())
                .filter(adventureType -> adventureType.toString().startsWith("ADV_"))
                .forEach(adventureType -> explorationAdventuresDeck.add(new AdventureCard(
                                adventureType,
                                ConfigReader.loadValue(EventType.class, ADVENTURE_PREFIX, adventureType.toString(), "EVENT")
                        )
                ));
        Collections.shuffle(explorationAdventuresDeck);

        logger.info("EXPLORATION ADVENTURES DECK has been created.");
        return explorationAdventuresDeck;
    }

    private LinkedList<AdventureCard> resourcesAdventuresDeckInitialization() {
        LinkedList<AdventureCard> resourcesAdventuresDeck = new LinkedList<>();
        Arrays.stream(AdventureType.values())
                .filter(adventureType -> adventureType.toString().startsWith("RES_"))
                .forEach(adventureType -> resourcesAdventuresDeck.add(new AdventureCard(
                                adventureType,
                                ConfigReader.loadValue(EventType.class, ADVENTURE_PREFIX, adventureType.toString(), "EVENT")
                        )
                ));
        Collections.shuffle(resourcesAdventuresDeck);

        logger.info("RESOURCES ADVENTURES DECK has been initialized.");
        return resourcesAdventuresDeck;
    }

    private LinkedList<BeastCard> beastsDeckInitialization() {
        LinkedList<BeastCard> beastsDeck = new LinkedList<>();
        Arrays.asList(BeastType.values()).forEach(beastType -> beastsDeck.add(new BeastCard(
                beastType,
                ConfigReader.loadValue(Integer.class, BEAST_PREFIX, beastType.toString(), "STRENGTH"),
                ConfigReader.loadValue(Integer.class, BEAST_PREFIX, beastType.toString(), "DAMAGE"),
                ConfigReader.loadValue(Integer.class, BEAST_PREFIX, beastType.toString(), "FOOD"),
                ConfigReader.loadValue(Integer.class, BEAST_PREFIX, beastType.toString(), "FURS")
        )));
        Collections.shuffle(beastsDeck);

        logger.info("BEASTS DECK has been initialized.");
        return beastsDeck;
    }

    private LinkedList<MysteryCard> mysteriesDeckInitialization() {
        LinkedList<MysteryCard> mysteriesDeck = new LinkedList<>();
        Arrays.asList(MysteryType.values()).forEach(mysteryType -> mysteriesDeck.add(new model.cards.MysteryCard(mysteryType)));
        Collections.shuffle(mysteriesDeck);

        logger.info("MYSTERIES DECK has been initialized.");
        return mysteriesDeck;
    }

    private LinkedList<DiscoveryToken> discoveriesStackInitialization() {
        LinkedList<DiscoveryToken> discoveryTokensDeck = new LinkedList<>();
        Arrays.asList(DiscoveryTokenType.values()).forEach(discoveryToken -> discoveryTokensDeck.add(
                new DiscoveryToken(discoveryToken)));
        Collections.shuffle(discoveryTokensDeck);

        logger.info("DISCOVERY TOKENS STACK has been initialized.");
        return discoveryTokensDeck;
    }

    private LinkedList<IslandTile> islandTilesStackInitialization() {
        LinkedList<IslandTile> islandTilesDeck = new LinkedList<>();
        for (int i = 1; i <= 11; i++) {
            IslandTile islandTile = new IslandTile(
                    i,
                    ConfigReader.loadValue(TerrainType.class, ISLAND_PREFIX, String.valueOf(i), "TERRAIN"),
                    ConfigReader.loadValue(ResourceType.class, ISLAND_PREFIX, String.valueOf(i), "LEFT_RESOURCE"),
                    ConfigReader.loadValue(ResourceType.class, ISLAND_PREFIX, String.valueOf(i), "RIGHT_RESOURCE"),
                    ConfigReader.loadValue(Integer.class, ISLAND_PREFIX, String.valueOf(i), "TOKENS"),
                    ConfigReader.loadValue(Boolean.class, ISLAND_PREFIX, String.valueOf(i), "TOTEM"),
                    ConfigReader.loadValue(Boolean.class, ISLAND_PREFIX, String.valueOf(i), "NATURAL_SHELTER")
            );
            islandTilesDeck.add(islandTile);
            Collections.shuffle(islandTilesDeck);
            islandTilesDeck.sort((t1, t2) -> t1.getTileID() == 8 ? -1 : t2.getTileID() == 8 ? 1 : 0);
        }

        logger.info("ISLAND TILES STACK has been initialized.");
        return islandTilesDeck;
    }

    private LinkedList<IdeaCard> ideasDeckInitialization() {
        LinkedList<IdeaCard> ideaCardsDeck = new LinkedList<>();
        Arrays.stream(IdeaType.values())
                .filter(ideaType ->
                        !ideaType.toString().startsWith("SCN_") && !ideaType.toString().startsWith("PARAM_") &&
                                !ConfigReader.loadValue(Boolean.class, IDEA_PREFIX, ideaType.toString(), "INITIAL"))
                .forEach(ideaType -> ideaCardsDeck.add(new IdeaCard(
                        ideaType,
                        ConfigReader.loadValue(ProfessionType.class, IDEA_PREFIX, ideaType.toString(), "OWNER"),
                        ConfigReader.loadValue(Boolean.class, IDEA_PREFIX, ideaType.toString(), "INITIAL"),
                        true,
                        ConfigReader.loadValue(Boolean.class, IDEA_PREFIX, ideaType.toString(), "MULTIPLE")
                )));
        Collections.shuffle(ideaCardsDeck);

        logger.info("IDEAS DECK has been initialized.");
        return ideaCardsDeck;
    }

    private LinkedList<StartingItemCard> startingItemsInitialization() {
        LinkedList<StartingItemCard> startingItemsDeck = new LinkedList<>();
        Arrays.asList(StartingItemType.values()).forEach(startingItemType -> startingItemsDeck.add(new StartingItemCard(startingItemType)));
        Collections.shuffle(startingItemsDeck);

        logger.info("STARTING ITEMS DECK has been initialized.");
        return startingItemsDeck;
    }

    private List<IdeaCard> drawInitialIdeas(LinkedList<IdeaCard> ideasDeck, int scenarioID) {
        List<IdeaCard> initialIdeas = new ArrayList<>();
        String scenarioInvention1 = ConfigReader.loadValue(String.class, SCENARIO_PREFIX, String.valueOf(scenarioID), "IDEA_1");
        String scenarioInvention2 = ConfigReader.loadValue(String.class, SCENARIO_PREFIX, String.valueOf(scenarioID), "IDEA_2");
        Arrays.stream(IdeaType.values())
                .filter(ideaType -> ideaType.toString().equals(scenarioInvention1) ||
                        ideaType.toString().equals(scenarioInvention2) ||
                        ConfigReader.loadValue(Boolean.class, IDEA_PREFIX, ideaType.toString(), "INITIAL"))
                .forEach(ideaType -> initialIdeas.add(new IdeaCard(
                        ideaType,
                        ConfigReader.loadValue(ProfessionType.class, IDEA_PREFIX, ideaType.toString(), "OWNER"),
                        ConfigReader.loadValue(Boolean.class, IDEA_PREFIX, ideaType.toString(), "INITIAL"),
                        false,
                        ConfigReader.loadValue(Boolean.class, IDEA_PREFIX, ideaType.toString(), "MULTIPLE")
                )));

        for (int i = 0; i < 5; i++) {
            initialIdeas.add(ideasDeck.removeFirst());
        }

        StringBuilder sb = new StringBuilder();
        initialIdeas.forEach(ideaCard -> sb.append(ideaCard.getIdea().toString()).append(" "));
        logger.info("Following INITIAL IDEAS has been drawn: " + sb.toString());
        return initialIdeas;
    }

    private List<StartingItemCard> drawStartingItems(LinkedList<StartingItemCard> startingItemsDeck) {
        List<StartingItemCard> startingItems = new ArrayList<>();
        int startingItemsNumber = options.getStartingItemsNumber();

        for (int i = 0; i < startingItemsNumber; i++) {
            startingItems.add(startingItemsDeck.removeFirst());
        }

        StringBuilder sb = new StringBuilder();
        startingItems.forEach(startingItemCard -> sb.append(startingItemCard.getItemType().toString()).append(" "));
        logger.info("Following STARTING ITEMS has been drawn: " + sb.toString());
        return startingItems;
    }
}
