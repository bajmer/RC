package engine;

import controller.GameWindowController;
import engine.phase.ActionPhase;
import engine.phase.PhaseHandler;
import engine.phase.ProductionPhase;
import engine.phase.WeatherPhase;
import model.board.Board;
import model.cards.BeastCard;
import model.cards.IdeaCard;
import model.character.Character;
import model.character.FridayCharacter;
import model.character.MainCharacter;
import model.data.GlobalData;
import model.elements.tiles.DiscoveryToken;
import model.elements.tiles.IslandTile;
import model.enums.ActionType;
import model.enums.TerrainType;
import model.enums.elements.ResourceType;
import model.enums.elements.TokenType;
import model.enums.elements.dices.DiceType;
import model.enums.elements.dices.DiceWallType;
import model.enums.elements.dices.DicesGroupType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class MethodRepository {
    private static Logger logger = LogManager.getLogger(MethodRepository.class);

    public static void changeFoodLevel(int value) {
        int beginFoodAmount = GlobalData.getAvailableResources().getFoodAmount();
        int newFoodAmount = beginFoodAmount + value;
        if (newFoodAmount < 0) {
            changeLongTermFoodLevel(newFoodAmount);
            newFoodAmount = 0;
        }
        GlobalData.getAvailableResources().setFoodAmount(newFoodAmount);
        if (newFoodAmount != beginFoodAmount) {
            logger.info("FOOD amount has been changed to: " + newFoodAmount);
        }
    }

    public static void changeLongTermFoodLevel(int value) {
        int beginLongTermFoodAmount = GlobalData.getAvailableResources().getLongTermFoodAmount();
        int newLongTermFoodAmount = beginLongTermFoodAmount + value;
        if (newLongTermFoodAmount < 0) {
            decreaseLivesOfAllMainCharacters(newLongTermFoodAmount);
            newLongTermFoodAmount = 0;
        }
        GlobalData.getAvailableResources().setLongTermFoodAmount(newLongTermFoodAmount);
        if (newLongTermFoodAmount != beginLongTermFoodAmount) {
            logger.info("LONG-TERM FOOD amount has been changed to: " + newLongTermFoodAmount);
        }

    }

    public static void changeWoodLevel(int value) {
        int beginWoodAmount = GlobalData.getAvailableResources().getWoodAmount();
        int newWoodAmount = beginWoodAmount + value;
        if (newWoodAmount < 0) {
            decreaseLivesOfAllMainCharacters(newWoodAmount);
            newWoodAmount = 0;
        }
        GlobalData.getAvailableResources().setWoodAmount(newWoodAmount);
        if (newWoodAmount != beginWoodAmount) {
            logger.info("WOOD amount has been changed to: " + newWoodAmount);
        }
    }

    public static void changeFurLevel(int value) {
        int beginFurAmount = GlobalData.getAvailableResources().getFurAmount();
        int newFurAmount = beginFurAmount + value;
        if (newFurAmount < 0) {
            decreaseLivesOfAllMainCharacters(newFurAmount);
            newFurAmount = 0;
        }
        GlobalData.getAvailableResources().setFurAmount(newFurAmount);
        if (newFurAmount != beginFurAmount) {
            logger.info("FURS amount has been changed to: " + newFurAmount);
        }
    }

    public static void changeMoraleLevel(int value) {
        final int MIN = -3;
        final int MAX = 3;

        int beginMoraleLevel = GlobalData.getLevels().getMoraleLevel();
        int newMoraleLevel = beginMoraleLevel + value;
        newMoraleLevel = (newMoraleLevel > MAX) ? MAX : (newMoraleLevel < MIN) ? MIN : newMoraleLevel;
        GlobalData.getLevels().setMoraleLevel(newMoraleLevel);
        if (newMoraleLevel != beginMoraleLevel) {
            logger.info("MORALE has been changed to level: " + newMoraleLevel);
        }
    }

    public static void changeRoofLevel(int value) {
        int beginRoofLevel = GlobalData.getLevels().getRoofLevel();
        int newRoofLevel = beginRoofLevel + value;
        if (newRoofLevel < 0) {
            decreaseLivesOfAllMainCharacters(newRoofLevel);
            newRoofLevel = 0;
        }
        GlobalData.getLevels().setRoofLevel(newRoofLevel);
        if (newRoofLevel != beginRoofLevel) {
            logger.info("ROOF has been changed to level: " + newRoofLevel);
        }
    }

    public static void changePalisadeLevel(int value) {
        int beginPalisadeLevel = GlobalData.getLevels().getPalisadeLevel();
        int newPalisadeLevel = beginPalisadeLevel + value;
        if (newPalisadeLevel < 0) {
            decreaseLivesOfAllMainCharacters(newPalisadeLevel);
            newPalisadeLevel = 0;
        }
        GlobalData.getLevels().setPalisadeLevel(newPalisadeLevel);
        if (newPalisadeLevel != beginPalisadeLevel) {
            logger.info("PALISADE has been changed to level: " + newPalisadeLevel);
        }
    }

    public static void changeWeaponLevel(int value, Character character) {
        int beginWeaponLevel = GlobalData.getLevels().getWeaponLevel();
        int newWeaponLevel = beginWeaponLevel + value;
        if (newWeaponLevel < 0) {
            if (character != null) {
                changeLivesLevel(newWeaponLevel, character);
            } else {
                decreaseLivesOfAllMainCharacters(newWeaponLevel);
            }
            newWeaponLevel = 0;
        }
        GlobalData.getLevels().setWeaponLevel(newWeaponLevel);
        if (newWeaponLevel != beginWeaponLevel) {
            logger.info("WEAPON has been changed to level: " + newWeaponLevel);
        }
    }

    public static void decreaseLivesOfAllMainCharacters(int value) {
        logger.debug("Reducing the lives level of all main characters.");
        GlobalData.getCharacters().stream()
                .filter(character -> character instanceof MainCharacter)
                .forEach(character -> changeLivesLevel(value, character));
    }

    public static void decreaseDeterminationOfAllMainCharacters(int value) {
        logger.debug("Reducing determination of all main characters.");
        GlobalData.getCharacters().stream()
                .filter(character -> character instanceof MainCharacter)
                .forEach(character -> changeDeterminationLevel(value, character));
    }

    public static void handleDiscoveryOfNewIslandTile(int positionOnBoard) {
        IslandTile discoveredTile = GlobalData.getDecks().getIslandTilesStack().removeFirst();
        GlobalData.getDiscoveredIslandTiles().add(discoveredTile);
        GlobalData.getBoard().getTilePositionIdToIslandTile().put(positionOnBoard, discoveredTile);
        GlobalData.getBoard().getIslandTileToTilePosition().put(discoveredTile, positionOnBoard);
        MethodRepository.updateAvailableTerrainTypes();

        if (discoveredTile.getLeftSource() == ResourceType.ANIMAL) {
            BeastCard beastCard = GlobalData.getDecks().getBeastCardsDeck().removeFirst();
            GlobalData.getAvailableBeastCards().addFirst(beastCard);
        }
        if (discoveredTile.getRightSource() == ResourceType.ANIMAL) {
            BeastCard beastCard = GlobalData.getDecks().getBeastCardsDeck().removeFirst();
            GlobalData.getAvailableBeastCards().addFirst(beastCard);
        }

        boolean totem = discoveredTile.isTotem();
        logger.info("Totem: " + totem);
        if (totem) {
            handleTotem();
        }

        int discoveryTokensNumber = discoveredTile.getDiscoveryTokensNumber();
        for (int i = 0; i < discoveryTokensNumber; i++) {
            DiscoveryToken discoveryToken = GlobalData.getDecks().getDiscoveryTokensStack().removeFirst();
            logger.info("Discovery token: " + discoveryToken.getDiscoveryToken().toString());
            GlobalData.getFutureResources().getDiscoveryTokens().add(discoveryToken);
        }
    }

    private static void handleTotem() {
        int scenarioId = GlobalData.getScenario().getId();
        int totemCounter = GlobalData.getScenario().getTotemCounter() + 1;
        GlobalData.getScenario().setTotemCounter(totemCounter);
        switch (scenarioId) {
            case 1:
                logger.info("Totem number: " + totemCounter + ", Effect: No effect");
                break;
            case 2:
                if (totemCounter == 1) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Dark temple");
                } else if (totemCounter == 2) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Altar of death");
                } else if (totemCounter > 2) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Hideout of cultists");
                }
                break;
            case 3:
                if (totemCounter == 1) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Slippery rocks");
                } else if (totemCounter == 2) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Old necropolis");
                } else if (totemCounter == 3) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Remains of mast");
                } else if (totemCounter == 4) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Balloon wreck");
                }

                break;
            case 4:
                if (totemCounter == 1) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Small temple");
                } else if (totemCounter == 2) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Ruins of the village");
                } else if (totemCounter == 3) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Hidden cave");
                } else if (totemCounter == 4) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Underground temple");
                }
                break;
            case 5:
                if (totemCounter == 1) {
                    logger.info("Totem number: " + totemCounter + ", Effect: First village");
                } else if (totemCounter == 2) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Second village");
                } else if (totemCounter == 3) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Third village");
                } else if (totemCounter == 4) {
                    logger.info("Totem number: " + totemCounter + ", Effect: Fourth village");
                }
                break;
            case 6:
                break;
        }
    }

    public static void handleMovingShelterToNewTile(IslandTile newCamp) {
        IslandTile oldCamp = GlobalData.getCamp();
        if (GlobalData.isShelter()) {
            MethodRepository.changeRoofLevel(-(int) Math.floor(GlobalData.getLevels().getRoofLevel() / 2));
            MethodRepository.changePalisadeLevel(-(int) Math.floor(GlobalData.getLevels().getPalisadeLevel() / 2));
        } else {
            MethodRepository.changeRoofLevel(-GlobalData.getLevels().getRoofLevel());
            MethodRepository.changePalisadeLevel(-GlobalData.getLevels().getPalisadeLevel());
        }

        GlobalData.getDiscoveredIslandTiles().forEach(islandTile -> {
            if (islandTile.getTokens().contains(TokenType.SHORTCUT_TOKEN)) {
                islandTile.getTokens().remove(TokenType.SHORTCUT_TOKEN);
                // TODO: 2018-12-13 obrócić przedmiot "skrót" na stronę pomysłu
            }
        });

        if (oldCamp.getTokens().contains(TokenType.ONE_MORE_WOOD_TOKEN)) {
            oldCamp.getTokens().remove(TokenType.ONE_MORE_WOOD_TOKEN);
            newCamp.getTokens().add(TokenType.ONE_MORE_WOOD_TOKEN);
        }
        if (oldCamp.getTokens().contains(TokenType.ONE_MORE_FOOD_TOKEN)) {
            oldCamp.getTokens().remove(TokenType.ONE_MORE_FOOD_TOKEN);
            newCamp.getTokens().add(TokenType.ONE_MORE_FOOD_TOKEN);
        }
    }

    public static void updateAvailableTerrainTypes() {
        GlobalData.getAvailableTerrainTypes().clear();
        GlobalData.getDiscoveredIslandTiles().forEach(islandTile -> {
            if (!islandTile.isCoveredTerrain()) {
                GlobalData.getAvailableTerrainTypes().add(islandTile.getTerrain());
            }
        });
    }

    public static void coverUncoverTerrainTypeIfPossible(TerrainType terrain, boolean cover) {
        List<IslandTile> possibleIslandTiles = new ArrayList<>();
        if (cover) {
            GlobalData.getDiscoveredIslandTiles().stream()
                    .filter(islandTile -> islandTile.getTerrain() == terrain && !islandTile.isCoveredTerrain())
                    .forEach(possibleIslandTiles::add);
        } else {
            GlobalData.getDiscoveredIslandTiles().stream()
                    .filter(islandTile -> islandTile.getTerrain() == terrain && islandTile.isCoveredTerrain())
                    .forEach(possibleIslandTiles::add);
        }
        if (!possibleIslandTiles.isEmpty()) {
            IslandTile chosenIslandTile = GameWindowController.chooseIslandTile(possibleIslandTiles);
            if (cover) {
                chosenIslandTile.setCoveredTerrain(true);
            } else {
                chosenIslandTile.setCoveredTerrain(false);
            }
            updateAvailableTerrainTypes();
        }
    }

    public static void nextRound() {
        int round = GlobalData.getScenario().getRound();
        int allRounds = GlobalData.getScenario().getAllRounds();
        round += 1;
        if (round <= allRounds) {
            GlobalData.getScenario().setRound(round);
        } else {
            // TODO: 2018-11-21 The end
        }
    }

    public static DiceWallType roll(DicesGroupType groupType, DiceType diceType) {
        Map<DiceType, List<DiceWallType>> dice = GlobalData.getDices().getDiceMap().get(groupType).stream()
                .filter(diceTypeListMap -> diceTypeListMap.containsKey(diceType))
                .findFirst().get();

        return dice.get(diceType).get(new Random().nextInt(6));
    }

    public static void handleBookIcon() {

    }

    public static void changeLivesLevel(int value, Character character) {
        int lives = character.getLives();
        int maxLives = character.getMaxLives();
        int newValue = lives + value;

        if (newValue > maxLives) {
            character.setLives(maxLives);
        } else if (newValue <= 0) {
            character.setLives(0);
        } else {
            character.setLives(newValue);
        }

        if (character instanceof MainCharacter) {
            MainCharacter mainCharacter = (MainCharacter) character;
            if (value < 0) {
                for (int i = lives; i >= newValue; i--) {
                    if (mainCharacter.getMoraleDown().contains(i)) {
                        changeMoraleLevel(-1);
                    }
                }
            }
            logger.debug("The number of " + mainCharacter.getProfession().toString() + " lives has been changed to: " + mainCharacter.getLives());
            if (mainCharacter.getLives() <= 0) {
                // TODO: 2018-12-07 Game over!
            }
        } else if (character instanceof FridayCharacter) {
            FridayCharacter fridayCharacter = (FridayCharacter) character;
            logger.debug("The number of FRIDAY lives has been changed to: " + fridayCharacter.getLives());
            if (fridayCharacter.getLives() <= 0) {
                // TODO: handle friday death
                fridayCharacter.getMarkers().clear();
            }
        }
    }

    public static void changeDeterminationLevel(int value, Character character) {
        int beginDetermination = character.getDetermination();
        int newDetermination = beginDetermination + value;
        if (newDetermination < 0) {
            changeLivesLevel(newDetermination, character);
            character.setDetermination(0);
        } else {
            character.setDetermination(newDetermination);
        }
        if (character instanceof MainCharacter) {
            logger.debug("The number of " + ((MainCharacter) character).getProfession().toString() + " determinations has been changed to: " + character.getDetermination());
        } else if (character instanceof FridayCharacter) {
            logger.debug("The number of FRIDAY determinations has been changed to: " + character.getDetermination());
        }
    }

    public static void nextFirstPlayer() {
        int mainCharactersAmount = GlobalData.getMainCharactersNumber();
        int firstPlayerId = (GlobalData.getScenario().getRound() + GlobalData.getFirstPlayerModifier()) % mainCharactersAmount;
        GlobalData.setFirstPlayer((MainCharacter) GlobalData.getCharacters().get(firstPlayerId));
        logger.debug("First player: " + GlobalData.getFirstPlayer().getProfession());
    }

    public static void putBeachTileOnTheBoard() {

    }

    public static void changeRoofOrPalisadeLevelByValue(int value) {
        String choice = GameWindowController.chooseRoofOrPalisade();
        if (choice.equals("ROOF")) {
            MethodRepository.changeRoofLevel(value);
        } else if (choice.equals("PALISADE")) {
            MethodRepository.changePalisadeLevel(value);
        }
    }

    public static void decreaseRoofOrPalisadeLevelByHalf() {
        String choice = GameWindowController.chooseRoofOrPalisade();
        if (choice.equals("ROOF")) {
            MethodRepository.changeRoofLevel(-(int) Math.ceil(GlobalData.getLevels().getRoofLevel() / 2));
        } else if (choice.equals("PALISADE")) {
            MethodRepository.changePalisadeLevel(-(int) Math.ceil(GlobalData.getLevels().getPalisadeLevel() / 2));
        }
    }

    public static void putWeatherTokenOnWeatherField(TokenType token) {
        PhaseHandler.getPhases().stream()
                .filter(phase -> phase instanceof WeatherPhase)
                .map(phase -> (WeatherPhase) phase)
                .forEach(weatherPhase -> weatherPhase.getTokens().add(token));
    }

    public static void putAnimalsDiceOnWeatherField() {
        PhaseHandler.getPhases().stream()
                .filter(phase -> phase instanceof WeatherPhase)
                .map(phase -> (WeatherPhase) phase)
                .forEach(weatherPhase -> weatherPhase.setRollBeastDiceWhileWeatherPhase(true));
    }

    public static void putTokenOnActionField(ActionType action, TokenType token) {
        ActionPhase.getActionsTokens().get(action).add(token);
    }

    public static void blockSourceWhileProductionPhase(String source) {
        if (source.equals("FOOD")) {
            ProductionPhase.setNoFood(true);
        } else if (source.equals("WOOD")) {
            ProductionPhase.setNoWood(true);
        }
    }

    public static void addOneResourceWhileProductionPhase(String source) {
        if (source.equals("FOOD")) {
            ProductionPhase.setFoodPlusOne(true);
        } else if (source.equals("WOOD")) {
            ProductionPhase.setWoodPlusOne(true);
        }
    }

    public static void discardIdeas(int value) {
        List<IdeaCard> chosenIdeas = GameWindowController.chooseIdeaCards(GlobalData.getIdeas(), value);
        if (chosenIdeas.size() < value) {
            MethodRepository.decreaseLivesOfAllMainCharacters(chosenIdeas.size() - value);
        }
        GlobalData.getIdeas().removeAll(chosenIdeas);
    }

    public static void removeInventionIfPossible(int value, boolean idea) {
        List<IdeaCard> chosenInventions = GameWindowController.chooseIdeaCards(GlobalData.getInventions(), value);
        chosenInventions.forEach(ideaCard -> {
            ideaCard.setTransformedToInvention(false);
            GlobalData.getInventions().remove(ideaCard);
            if (idea) {
                GlobalData.getIdeas().add(ideaCard);
            }
            // TODO: 2018-12-14 anulować działanie
        });
    }

    private static List<IslandTile> getPossibleNeighboringIslandTiles() {
        List<IslandTile> possibleIslandTiles = new ArrayList<>();
        Board board = GlobalData.getBoard();
        List<Integer> neighbourPositions = board.getTilesPositionsIdInNeighbourhood().get(board.getIslandTileToTilePosition().get(GlobalData.getCamp()));
        neighbourPositions.forEach(position -> {
            IslandTile islandTile = board.getTilePositionIdToIslandTile().get(position);
            if (islandTile != null && !islandTile.isUpsideDown()) {
                possibleIslandTiles.add(islandTile);
            }
        });
        return possibleIslandTiles;
    }

    private static List<IslandTile> getTurnedDownIslandTiles() {
        List<IslandTile> possibleIslandTiles = new ArrayList<>();
        GlobalData.getDiscoveredIslandTiles().stream()
                .filter(IslandTile::isUpsideDown)
                .forEach(possibleIslandTiles::add);
        return possibleIslandTiles;
    }

    public static IslandTile upsideDownNeighboringIslandTileIfPossible() {
        List<IslandTile> possibleIslandTiles = getPossibleNeighboringIslandTiles();

        if (!possibleIslandTiles.isEmpty()) {
            IslandTile islandTile = GameWindowController.chooseIslandTile(possibleIslandTiles);
            islandTile.setUpsideDown(true);
            return islandTile;
        }
        return null;
    }

    public static void coverSourcesOnNeighboringIslandTiles(int value) {
        List<IslandTile> possibleIslandTiles = getPossibleNeighboringIslandTiles();

        Map<IslandTile, List<ResourceType>> possibleSources = new HashMap<>();
        possibleIslandTiles.forEach(islandTile -> {
            List<ResourceType> sources = new ArrayList<>();
            if (islandTile.getLeftSource() != ResourceType.ANIMAL && !islandTile.isEndOfLeftSource()) {
                sources.add(islandTile.getLeftSource());
            }
            if (islandTile.getRightSource() != ResourceType.ANIMAL && !islandTile.isEndOfRightSource()) {
                sources.add(islandTile.getRightSource());
            }
            if (!sources.isEmpty()) {
                possibleSources.put(islandTile, sources);
            }
        });

        Map<IslandTile, List<ResourceType>> chosenSource = GameWindowController.chooseSourcesToCover(possibleSources, value);
        chosenSource.forEach((islandTile, resourceTypes) -> {
            if (resourceTypes.size() == 1) {
                if (islandTile.getLeftSource() == resourceTypes.get(0)) {
                    islandTile.setEndOfLeftSource(true);
                } else if (islandTile.getRightSource() == resourceTypes.get(0)) {
                    islandTile.setEndOfRightSource(true);
                }
            } else {
                islandTile.setEndOfLeftSource(true);
                islandTile.setEndOfRightSource(true);
            }
        });

        if (chosenSource.size() < value) {
            decreaseLivesOfAllMainCharacters(chosenSource.size() - value);
        }
    }

    public static void coverOrUncoverSourceClosestToTheCamp(List<ResourceType> sources, boolean cover) {
        List<IslandTile> possibleIslandTile = new ArrayList<>();
        if (cover) {
            GlobalData.getDiscoveredIslandTiles().stream()
                    .filter(islandTile -> (sources.contains(islandTile.getLeftSource()) && !islandTile.isEndOfLeftSource())
                            || (sources.contains(islandTile.getRightSource()) && !islandTile.isEndOfRightSource()))
                    .forEach(possibleIslandTile::add);
        } else {
            GlobalData.getDiscoveredIslandTiles().stream()
                    .filter(islandTile -> (sources.contains(islandTile.getLeftSource()) && islandTile.isEndOfLeftSource())
                            || (sources.contains(islandTile.getRightSource()) && islandTile.isEndOfRightSource()))
                    .forEach(possibleIslandTile::add);
        }

        int positionOfTheCamp = GlobalData.getBoard().getIslandTileToTilePosition().get(GlobalData.getCamp());
        Map<Integer, List<IslandTile>> distancesMap = new TreeMap<>();
        distancesMap.put(0, new ArrayList<>());
        distancesMap.put(1, new ArrayList<>());
        distancesMap.put(2, new ArrayList<>());
        distancesMap.put(3, new ArrayList<>());
        possibleIslandTile.forEach(islandTile -> {
            int positionOfIslandTile = GlobalData.getBoard().getIslandTileToTilePosition().get(islandTile);
            int distanceBetweenIslandTileAndCamp = GlobalData.getBoard().getDistancesBetweenTilesPositions().get(Arrays.asList(positionOfTheCamp, positionOfIslandTile));
            distancesMap.get(distanceBetweenIslandTileAndCamp).add(islandTile);
        });

        Optional<Map.Entry<Integer, List<IslandTile>>> optional = distancesMap.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .findFirst();
        if (optional.isPresent()) {
            List<IslandTile> closestIslandTiles = optional.get().getValue();
            IslandTile chosenIslandTile = GameWindowController.chooseIslandTile(closestIslandTiles);
            if (sources.contains(chosenIslandTile.getLeftSource()) && sources.contains(chosenIslandTile.getRightSource())) {
                Map<IslandTile, List<ResourceType>> possibleSources = new HashMap<>();
                possibleSources.put(chosenIslandTile, sources);
                Map<IslandTile, List<ResourceType>> chosenSource = GameWindowController.chooseSourcesToCover(possibleSources, 1);
                if (chosenIslandTile.getLeftSource() == chosenSource.get(chosenIslandTile).get(0)) {
                    if (cover) {
                        chosenIslandTile.setEndOfLeftSource(true);
                    } else {
                        chosenIslandTile.setEndOfLeftSource(false);
                    }
                } else if (chosenIslandTile.getRightSource() == chosenSource.get(chosenIslandTile).get(0)) {
                    if (cover) {
                        chosenIslandTile.setEndOfRightSource(true);
                    } else {
                        chosenIslandTile.setEndOfRightSource(false);
                    }
                }
            } else {
                if (sources.contains(chosenIslandTile.getLeftSource())) {
                    if (cover) {
                        chosenIslandTile.setEndOfLeftSource(true);
                    } else {
                        chosenIslandTile.setEndOfLeftSource(false);
                    }
                } else if (sources.contains(chosenIslandTile.getRightSource())) {
                    if (cover) {
                        chosenIslandTile.setEndOfRightSource(true);
                    } else {
                        chosenIslandTile.setEndOfRightSource(false);
                    }
                }
            }
        } else {
            decreaseLivesOfAllMainCharacters(-1);
        }
    }

    public static void uncoverSourceByType(ResourceType source) {

    }

    public static void moveCampToNeighboringIslandTile() {
        IslandTile chosenIslandTile = GameWindowController.chooseIslandTile(MethodRepository.getPossibleNeighboringIslandTiles());
        if (chosenIslandTile != null) {
            MethodRepository.handleMovingShelterToNewTile(chosenIslandTile);
        } else {
            MethodRepository.decreaseLivesOfAllMainCharacters(-1);
        }
    }

    public static void chooseInventionOrWeaponOrPalisadeOrWound() {
        String choice = GameWindowController.chooseRoofOrPalisade();
        if (choice.equals("INVENTION")) {
            // TODO: 2018-12-13
        } else if (choice.equals("WEAPON")) {
            MethodRepository.changeWeaponLevel(-1, null);
        } else if (choice.equals("PALISADE")) {
            MethodRepository.changePalisadeLevel(-1);
        } else if (choice.equals("WOUND")) {
            decreaseLivesOfAllMainCharacters(-1);
        }
    }

    public static void takeOneCardFromIdeasDeck() {
        List<IdeaCard> chosenIdeas = GameWindowController.chooseIdeaCards(GlobalData.getDecks().getIdeaCardsDeck(), 1);
        if (chosenIdeas.size() == 1) {
            GlobalData.getIdeas().add(chosenIdeas.get(0));
        }
    }
}
