package model.elements;

import model.enums.elements.dices.DiceType;
import model.enums.elements.dices.DiceWallType;
import model.enums.elements.dices.DicesGroupType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigReader;

import java.util.*;

import static model.enums.elements.dices.DicesGroupType.*;

public class Dices {
    private Logger logger = LogManager.getLogger(Dices.class);

    private Map<DicesGroupType, Map<DiceType, List<DiceWallType>>> diceMap = new HashMap<>();

    public Dices() {
        //weather dices
        List<DiceWallType> weatherRainDiceWalls = new ArrayList<>();
        List<DiceWallType> weatherSnowDiceWalls = new ArrayList<>();
        List<DiceWallType> weatherAnimalDiceWalls = new ArrayList<>();
        String[] weatherRainDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(WEATHER_DICES), "WALL_1").split(",");
        String[] weatherSnowDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(WEATHER_DICES), "WALL_2").split(",");
        String[] weatherAnimalDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(WEATHER_DICES), "WALL_3").split(",");
        Arrays.stream(weatherRainDiceWallsString).forEach(s -> weatherRainDiceWalls.add(DiceWallType.valueOf(s)));
        Arrays.stream(weatherSnowDiceWallsString).forEach(s -> weatherSnowDiceWalls.add(DiceWallType.valueOf(s)));
        Arrays.stream(weatherAnimalDiceWallsString).forEach(s -> weatherAnimalDiceWalls.add(DiceWallType.valueOf(s)));
        Map<DiceType, List<DiceWallType>> weatherRainMap = new HashMap<>();
        Map<DiceType, List<DiceWallType>> weatherSnowMap = new HashMap<>();
        Map<DiceType, List<DiceWallType>> weatherAnimalMap = new HashMap<>();
        weatherRainMap.put(DiceType.RAIN, weatherRainDiceWalls);
        weatherSnowMap.put(DiceType.SNOW, weatherSnowDiceWalls);
        weatherAnimalMap.put(DiceType.ANIMALS, weatherAnimalDiceWalls);

        //building dices
        List<DiceWallType> buildingSuccessDiceWalls = new ArrayList<>();
        List<DiceWallType> buildingAdventureDiceWalls = new ArrayList<>();
        List<DiceWallType> buildingWoundDiceWalls = new ArrayList<>();
        Map<DiceType, List<DiceWallType>> buildingSuccessMap = new HashMap<>();
        Map<DiceType, List<DiceWallType>> buildingAdventuresMap = new HashMap<>();
        Map<DiceType, List<DiceWallType>> buildingWoundMap = new HashMap<>();
        String[] buildingSuccessDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(BUILDING_DICES), "WALL_1").split(",");
        String[] buildingAdventureDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(BUILDING_DICES), "WALL_2").split(",");
        String[] buildingWoundDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(BUILDING_DICES), "WALL_3").split(",");
        Arrays.stream(buildingSuccessDiceWallsString).forEach(s -> buildingSuccessDiceWalls.add(DiceWallType.valueOf(s)));
        Arrays.stream(buildingAdventureDiceWallsString).forEach(s -> buildingAdventureDiceWalls.add(DiceWallType.valueOf(s)));
        Arrays.stream(buildingWoundDiceWallsString).forEach(s -> buildingWoundDiceWalls.add(DiceWallType.valueOf(s)));
        buildingSuccessMap.put(DiceType.SUCCESS, buildingSuccessDiceWalls);
        buildingAdventuresMap.put(DiceType.ADVENTURE, buildingAdventureDiceWalls);
        buildingWoundMap.put(DiceType.WOUND, buildingWoundDiceWalls);

        //exploration dices
        List<DiceWallType> explorationSuccessDiceWalls = new ArrayList<>();
        List<DiceWallType> explorationAdventureDiceWalls = new ArrayList<>();
        List<DiceWallType> explorationWoundDiceWalls = new ArrayList<>();
        Map<DiceType, List<DiceWallType>> explorationSuccessMap = new HashMap<>();
        Map<DiceType, List<DiceWallType>> explorationAdventuresMap = new HashMap<>();
        Map<DiceType, List<DiceWallType>> explorationWoundMap = new HashMap<>();
        String[] explorationSuccessDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(EXPLORATION_DICES), "WALL_1").split(",");
        String[] explorationAdventureDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(EXPLORATION_DICES), "WALL_2").split(",");
        String[] explorationWoundDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(EXPLORATION_DICES), "WALL_3").split(",");
        Arrays.stream(explorationSuccessDiceWallsString).forEach(s -> explorationSuccessDiceWalls.add(DiceWallType.valueOf(s)));
        Arrays.stream(explorationAdventureDiceWallsString).forEach(s -> explorationAdventureDiceWalls.add(DiceWallType.valueOf(s)));
        Arrays.stream(explorationWoundDiceWallsString).forEach(s -> explorationWoundDiceWalls.add(DiceWallType.valueOf(s)));
        explorationSuccessMap.put(DiceType.SUCCESS, explorationSuccessDiceWalls);
        explorationAdventuresMap.put(DiceType.ADVENTURE, explorationAdventureDiceWalls);
        explorationWoundMap.put(DiceType.WOUND, explorationWoundDiceWalls);

        //resources dices
        List<DiceWallType> resourcesSuccessDiceWalls = new ArrayList<>();
        List<DiceWallType> resourcesAdventureDiceWalls = new ArrayList<>();
        List<DiceWallType> resourcesWoundDiceWalls = new ArrayList<>();
        Map<DiceType, List<DiceWallType>> resourcesSuccessMap = new HashMap<>();
        Map<DiceType, List<DiceWallType>> resourcesAdventuresMap = new HashMap<>();
        Map<DiceType, List<DiceWallType>> resourcesWoundMap = new HashMap<>();
        String[] resourcesSuccessDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(RESOURCES_DICES), "WALL_1").split(",");
        String[] resourcesAdventureDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(RESOURCES_DICES), "WALL_2").split(",");
        String[] resourcesWoundDiceWallsString = ConfigReader.loadValue(String.class, "DICE", String.valueOf(RESOURCES_DICES), "WALL_3").split(",");
        Arrays.stream(resourcesSuccessDiceWallsString).forEach(s -> resourcesSuccessDiceWalls.add(DiceWallType.valueOf(s)));
        Arrays.stream(resourcesAdventureDiceWallsString).forEach(s -> resourcesAdventureDiceWalls.add(DiceWallType.valueOf(s)));
        Arrays.stream(resourcesWoundDiceWallsString).forEach(s -> resourcesWoundDiceWalls.add(DiceWallType.valueOf(s)));
        resourcesSuccessMap.put(DiceType.SUCCESS, resourcesSuccessDiceWalls);
        resourcesAdventuresMap.put(DiceType.ADVENTURE, resourcesAdventureDiceWalls);
        resourcesWoundMap.put(DiceType.WOUND, resourcesWoundDiceWalls);

        diceMap.put(WEATHER_DICES, weatherRainMap);
        diceMap.put(WEATHER_DICES, weatherSnowMap);
        diceMap.put(WEATHER_DICES, weatherAnimalMap);
        diceMap.put(BUILDING_DICES, buildingSuccessMap);
        diceMap.put(BUILDING_DICES, buildingAdventuresMap);
        diceMap.put(BUILDING_DICES, buildingWoundMap);
        diceMap.put(EXPLORATION_DICES, explorationSuccessMap);
        diceMap.put(EXPLORATION_DICES, explorationAdventuresMap);
        diceMap.put(EXPLORATION_DICES, explorationWoundMap);
        diceMap.put(RESOURCES_DICES, resourcesSuccessMap);
        diceMap.put(RESOURCES_DICES, resourcesAdventuresMap);
        diceMap.put(RESOURCES_DICES, resourcesWoundMap);
    }

    public DiceWallType roll(DicesGroupType groupType, DiceType diceType) {
        return diceMap.get(groupType).get(diceType).get(new Random().nextInt(6));
    }

}
