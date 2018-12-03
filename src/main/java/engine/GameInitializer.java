package engine;

import model.board.Board;
import model.cards.*;
import model.character.Character;
import model.character.FridayCharacter;
import model.character.MainCharacter;
import model.data.GlobalData;
import model.decks.Decks;
import model.elements.Dices;
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
import utils.ConfigReader;

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

	GlobalData initialize() {

//		creating scenario, characters, startingItems, etc.
		Scenario scenario = createScenario();
		List<Character> characters = createCharacters();
		List<StartingItemCard> startingItems = createStartingItems();

//		creating all stacks
		LinkedList<EventCard> eventsDeck = createEventsDeck(scenario.getAllRounds());
		LinkedList<AdventureCard> buildingAdventuresDeck = createBuildingAdventuresDeck();
		LinkedList<AdventureCard> explorationAdventuresDeck = createExplorationAdventuresDeck();
		LinkedList<AdventureCard> resourcesAdventuresDeck = createResourcesAdventuresDeck();
		LinkedList<BeastCard> beastsDeck = createBeastsDeck();
		LinkedList<MysteryCard> mysteriesDeck = createMysteriesDeck();
		LinkedList<DiscoveryToken> discoveriesStack = createDiscoveriesStack();
		LinkedList<IslandTile> islandTilesStack = createIslandTilesStack();
		LinkedList<IdeaCard> inventionsDeck = createIdeasDeck();

		List<IdeaCard> ideas = createInitialIdeas(inventionsDeck, scenario.getId());

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

		return new GlobalData(decks, new Dices(), new Board(), scenario, characters, startingItems, ideas);
	}

	private Scenario createScenario() {
		int scenarioID = options.getScenarioNumber();
		Scenario scenario = new Scenario(
				scenarioID,
				ConfigReader.loadValue(Integer.class, SCENARIO_PREFIX, Integer.toString(scenarioID), "ALL_ROUNDS")
		);
		logger.info("SCENARIO has been created.");

		return scenario;
	}

	private List<Character> createCharacters() {
		List<Character> characters = new ArrayList<>();
		options.getCharacters().forEach(chosenCharacter -> {
			ProfessionType profession = chosenCharacter.getProfession();
			SexType sex = chosenCharacter.getSex();
			String moraleDownString = ConfigReader.loadValue(String.class, CHARACTER_PREFIX, profession.toString(), "MORALE_DOWN");
			List<String> moraleDownStringArray = new ArrayList<>(Arrays.asList(moraleDownString.split("-")));
			List<Integer> moraleDownIntArray = new ArrayList<>();
			moraleDownStringArray.forEach(s -> moraleDownIntArray.add(Integer.parseInt(s)));
			characters.add(new MainCharacter(
					profession,
					sex,
					new ArrayList<>(Arrays.asList(
							ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_1"),
							ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_2"),
							ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_3"),
							ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_4"),
							ConfigReader.loadValue(SpecialSkillType.class, CHARACTER_PREFIX, profession.toString(), "SKILL_4")
					)),
					moraleDownIntArray,
					ConfigReader.loadValue(Integer.class, CHARACTER_PREFIX, profession.toString(), "LIVES")
			));
		});

		if (options.isActiveFriday()) {
			characters.add(new FridayCharacter());
		}

		logger.info("CHARACTERS has been created.");
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

		StringBuilder sb = new StringBuilder();
		startingItems.forEach(startingItemCard -> sb.append(startingItemCard.getItemType().toString()).append(" "));
		logger.info("Following STARTING ITEMS has been drawn: " + sb.toString());

		return startingItems;
	}

	private LinkedList<EventCard> createEventsDeck(int scenarioAllRounds) {
		LinkedList<EventCard> allEventsDeck = new LinkedList<>();
		Arrays.asList(EventType.values()).forEach(eventEffectType -> allEventsDeck.add(new EventCard(
				eventEffectType,
				ConfigReader.loadValue(IconType.class, EVENT_PREFIX, eventEffectType.toString(), "EVENT_ICON"),
				ConfigReader.loadValue(ThreatType.class, EVENT_PREFIX, eventEffectType.toString(), "THREAT_ACTION")
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

		logger.info("EVENTS DECK has been created.");
		return eventsDeck;
	}

	private LinkedList<AdventureCard> createBuildingAdventuresDeck() {
		LinkedList<AdventureCard> buildingAdventuresDeck = new LinkedList<>();
		Arrays.stream(AdventureType.values())
				.filter(adventureType -> adventureType.toString().startsWith("BUILD_"))
				.forEach(adventureType -> buildingAdventuresDeck.add(new AdventureCard(
						adventureType,
						ConfigReader.loadValue(EventType.class, ADVENTURE_PREFIX, adventureType.toString(), "EVENT_NAME")
				)
		));
		Collections.shuffle(buildingAdventuresDeck);

		logger.info("BUILDING ADVENTURES DECK has been created.");
		return buildingAdventuresDeck;
	}

	private LinkedList<AdventureCard> createExplorationAdventuresDeck() {
		LinkedList<AdventureCard> explorationAdventuresDeck = new LinkedList<>();
		Arrays.stream(AdventureType.values())
				.filter(adventureType -> adventureType.toString().startsWith("ADV_"))
				.forEach(adventureType -> explorationAdventuresDeck.add(new AdventureCard(
						adventureType,
						ConfigReader.loadValue(EventType.class, ADVENTURE_PREFIX, adventureType.toString(), "EVENT_NAME")
				)
		));
		Collections.shuffle(explorationAdventuresDeck);

		logger.info("EXPLORATION ADVENTURES DECK has been created.");
		return explorationAdventuresDeck;
	}

	private LinkedList<AdventureCard> createResourcesAdventuresDeck() {
		LinkedList<AdventureCard> resourcesAdventuresDeck = new LinkedList<>();
		Arrays.stream(AdventureType.values())
				.filter(adventureType -> adventureType.toString().startsWith("RES_"))
				.forEach(adventureType -> resourcesAdventuresDeck.add(new AdventureCard(
						adventureType,
						ConfigReader.loadValue(EventType.class, ADVENTURE_PREFIX, adventureType.toString(), "EVENT_NAME")
				)
		));
		Collections.shuffle(resourcesAdventuresDeck);

		logger.info("RESOURCES ADVENTURES DECK has been created.");
		return resourcesAdventuresDeck;
	}

	private LinkedList<BeastCard> createBeastsDeck() {
		LinkedList<BeastCard> beastsDeck = new LinkedList<>();
		Arrays.asList(BeastType.values()).forEach(beastType -> beastsDeck.add(new BeastCard(
				beastType,
				ConfigReader.loadValue(Integer.class, BEAST_PREFIX, beastType.toString(), "STRENGTH"),
				ConfigReader.loadValue(Integer.class, BEAST_PREFIX, beastType.toString(), "DAMAGE"),
				ConfigReader.loadValue(Integer.class, BEAST_PREFIX, beastType.toString(), "FOOD"),
				ConfigReader.loadValue(Integer.class, BEAST_PREFIX, beastType.toString(), "FURS")
		)));
		Collections.shuffle(beastsDeck);

		logger.info("BEASTS DECK has been created.");
		return beastsDeck;
	}

	private LinkedList<MysteryCard> createMysteriesDeck() {
		LinkedList<MysteryCard> mysteriesDeck = new LinkedList<>();
        Arrays.asList(MysteryType.values()).forEach(mysteryType -> mysteriesDeck.add(new model.cards.MysteryCard(mysteryType)));
		Collections.shuffle(mysteriesDeck);

		logger.info("MYSTERIES DECK has been created.");
		return mysteriesDeck;
	}

	private LinkedList<DiscoveryToken> createDiscoveriesStack() {
		LinkedList<DiscoveryToken> discoveryTokensDeck = new LinkedList<>();
		Arrays.asList(DiscoveryTokenType.values()).forEach(discoveryToken -> discoveryTokensDeck.add(
				new DiscoveryToken(discoveryToken)));
		Collections.shuffle(discoveryTokensDeck);

		logger.info("DISCOVERY TOKENS STACK has been created.");
		return discoveryTokensDeck;
	}

	private LinkedList<IslandTile> createIslandTilesStack() {
		LinkedList<IslandTile> islandTilesDeck = new LinkedList<>();
		for (int i = 1; i <= 11; i++) {
			IslandTile islandTile = new IslandTile(
					i,
					ConfigReader.loadValue(TerrainType.class, ISLAND_PREFIX, String.valueOf(i), "TERRAIN"),
					ConfigReader.loadValue(ResourceType.class, ISLAND_PREFIX, String.valueOf(i), "LEFT_RESOURCE"),
					ConfigReader.loadValue(ResourceType.class, ISLAND_PREFIX, String.valueOf(i), "RIGHT_RESOURCE"),
					ConfigReader.loadValue(Integer.class, ISLAND_PREFIX, String.valueOf(i), "TOTEMS"),
					ConfigReader.loadValue(Integer.class, ISLAND_PREFIX, String.valueOf(i), "TOKENS"),
					ConfigReader.loadValue(Boolean.class, ISLAND_PREFIX, String.valueOf(i), "NATURAL_SHELTER")
			);
			islandTilesDeck.add(islandTile);
			Collections.shuffle(islandTilesDeck);
			islandTilesDeck.sort((t1, t2) -> t1.getTileID() == 8 ? -1 : t2.getTileID() == 8 ? 1 : 0);
		}

		logger.info("ISLAND TILES STACK has been created.");
		return islandTilesDeck;
	}

	private LinkedList<IdeaCard> createIdeasDeck() {
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

		logger.info("IDEAS DECK has been created.");
		return ideaCardsDeck;
	}

	private List<IdeaCard> createInitialIdeas(LinkedList<IdeaCard> ideasDeck, int scenarioID) {
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
}
