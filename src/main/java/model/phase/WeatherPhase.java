package model.phase;

import model.data.GlobalData;
import model.enums.elements.dices.DiceType;
import model.enums.elements.dices.DiceWallType;
import model.enums.elements.dices.DicesGroupType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeatherPhase implements Phase {
	private final String separator = "-";
	private Logger logger = LogManager.getLogger(WeatherPhase.class);

	@Override
	public void initializePhase(GlobalData globalData) {
		logger.info("---> Phase: WEATHER");
	}

	@Override
	public void runPhase(GlobalData globalData) {
		int roundNumber = globalData.getScenario().getRound();
		String roundNumberString = String.valueOf(roundNumber);
		String dicesConfigString = ConfigReader.loadValue(String.class, "SCENARIO", roundNumberString, "WEATHER_ROUND_" + roundNumberString);
		List<DiceType> dices = new ArrayList<>();
		if (dicesConfigString.contains(separator)) {
			String[] dicesString = dicesConfigString.split(separator);
			Arrays.stream(dicesString).forEach(s -> dices.add(DiceType.valueOf(s)));
		} else {
			dices.add(DiceType.valueOf(dicesConfigString));
		}

		List<DiceWallType> results = new ArrayList<>();
		dices.stream().filter(diceType -> diceType != DiceType.NONE).forEach(diceType -> {
			results.add(globalData.getDices().roll(DicesGroupType.WEATHER_DICES, diceType));
		});

		int rainClouds = 0;
		rainClouds += Math.toIntExact(results.stream().filter(result -> result == DiceWallType.SINGLE_RAIN).count());
		rainClouds += Math.toIntExact(results.stream().filter(result -> result == DiceWallType.DOUBLE_RAIN).count()) * 2;
		int snowClouds = 0;
		snowClouds += Math.toIntExact(results.stream().filter(result -> result == DiceWallType.SINGLE_SNOW).count());
		snowClouds += Math.toIntExact(results.stream().filter(result -> result == DiceWallType.DOUBLE_SNOW).count()) * 2;
		boolean beastAttack = results.stream().anyMatch(result -> result == DiceWallType.BEAST_ATTACK);
		boolean palisadeDamage = results.stream().anyMatch(result -> result == DiceWallType.PALISADE_DAMAGE);
		boolean foodDiscard = results.stream().anyMatch(result -> result == DiceWallType.FOOD_DISCARD);

		if (snowClouds > 0) {
			logger.info("SNOW clouds number: " + snowClouds + ". You should discard " + snowClouds + " WOODS.");
			globalData.changeWoodLevel(-snowClouds);
		}

		int allClouds = rainClouds + snowClouds;
		int missingRoofLevel = allClouds - globalData.getGameParams().getRoofLevel();
		if (missingRoofLevel > 0) {
			logger.info("All clouds number: " + snowClouds + ". Roof level: " + globalData.getGameParams().getRoofLevel() + ". You should discard " + missingRoofLevel + " WOODS and " + missingRoofLevel + " FOODS");
			globalData.changeWoodLevel(-missingRoofLevel);
			globalData.changeFoodLevel(-missingRoofLevel);
		}

		if (beastAttack) {
			logger.info("Beast attack with strength 3!");
			// TODO: 2018-11-23 Handle beast attack
		} else if (palisadeDamage) {
			logger.info("The palisade level drops by 1!");
			globalData.changePalisadeLevel(-1);
		} else if (foodDiscard) {
			logger.info("You should discard 1 FOOD");
			globalData.changeFoodLevel(-1);
		}
	}
}
