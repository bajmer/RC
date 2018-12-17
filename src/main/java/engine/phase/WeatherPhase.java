package engine.phase;

import engine.MethodRepository;
import engine.utils.ConfigReader;
import model.data.GlobalData;
import model.enums.elements.TokenType;
import model.enums.elements.dices.DiceType;
import model.enums.elements.dices.DiceWallType;
import model.enums.elements.dices.DicesGroupType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class WeatherPhase implements Phase {
	private Logger logger = LogManager.getLogger(WeatherPhase.class);

    private static final String separator = "-";
    private Set<TokenType> tokens = new HashSet<>();
    private boolean rollBeastDiceWhileWeatherPhase = false;

	@Override
    public void initializePhase() {
		logger.info("---> Phase: WEATHER");
	}

	@Override
    public void runPhase() {
        int scenarioID = GlobalData.getScenario().getId();
        int roundNumber = GlobalData.getScenario().getRound();
        String scenarioIdString = String.valueOf(scenarioID);
        String roundNumberString = String.valueOf(roundNumber);
        String dicesConfigString = ConfigReader.loadValue(String.class, "SCENARIO", scenarioIdString, "WEATHER_ROUND_" + roundNumberString);
        List<DiceType> dices = new ArrayList<>();
        if (dicesConfigString.contains(separator)) {
            String[] dicesString = dicesConfigString.split(separator);
            Arrays.stream(dicesString).forEach(s -> dices.add(DiceType.valueOf(s)));
        } else {
            dices.add(DiceType.valueOf(dicesConfigString));
        }

        List<DiceWallType> results = new ArrayList<>();
        dices.stream()
                .filter(diceType -> diceType != DiceType.NONE)
                .forEach(diceType -> results.add(MethodRepository.roll(DicesGroupType.WEATHER_DICES, diceType)));
        if (!dices.contains(DiceType.ANIMALS) && rollBeastDiceWhileWeatherPhase) {
            results.add(MethodRepository.roll(DicesGroupType.WEATHER_DICES, DiceType.ANIMALS));
            rollBeastDiceWhileWeatherPhase = false;
        }

        int rainClouds = 0;
        rainClouds += Math.toIntExact(results.stream().filter(result -> result == DiceWallType.SINGLE_RAIN).count());
        rainClouds += Math.toIntExact(results.stream().filter(result -> result == DiceWallType.DOUBLE_RAIN).count()) * 2;
        rainClouds += tokens.contains(TokenType.RAIN_CLOUD_TOKEN) ? 1 : 0;
        int snowClouds = 0;
        snowClouds += Math.toIntExact(results.stream().filter(result -> result == DiceWallType.SINGLE_SNOW).count());
        snowClouds += Math.toIntExact(results.stream().filter(result -> result == DiceWallType.DOUBLE_SNOW).count()) * 2;
        snowClouds += tokens.contains(TokenType.SNOW_CLOUD_TOKEN) ? 1 : 0;

        boolean beastAttack = results.stream().anyMatch(result -> result == DiceWallType.BEAST_ATTACK);
        boolean palisadeDamage = results.stream().anyMatch(result -> result == DiceWallType.PALISADE_DAMAGE);
        boolean foodDiscard = results.stream().anyMatch(result -> result == DiceWallType.FOOD_DISCARD);

        if (snowClouds > 0) {
            logger.info("SNOW clouds number: " + snowClouds + ". You should discard " + snowClouds + " WOODS.");
            MethodRepository.changeWoodLevel(-snowClouds);
        }

        int allClouds = rainClouds + snowClouds;
        int missingRoofLevel = allClouds - GlobalData.getLevels().getRoofLevel();
        if (missingRoofLevel > 0) {
            logger.info("All clouds number: " + snowClouds + ". Roof level: " + GlobalData.getLevels().getRoofLevel() + ". You should discard " + missingRoofLevel + " WOODS and " + missingRoofLevel + " FOODS");
            MethodRepository.changeWoodLevel(-missingRoofLevel);
            MethodRepository.changeFoodLevel(-missingRoofLevel);
        }

        if (beastAttack) {
            logger.info("Beast attack with strength 3!");
            int beastStrength = 3;
            int weaponLevel = GlobalData.getLevels().getWeaponLevel();
            if (weaponLevel < beastStrength) {
                MethodRepository.decreaseLivesOfAllMainCharacters(weaponLevel - beastStrength);
            }
        } else if (palisadeDamage) {
            logger.info("The palisade level drops by 1!");
            MethodRepository.changePalisadeLevel(-1);
        } else if (foodDiscard) {
            logger.info("You should discard 1 FOOD");
            MethodRepository.changeFoodLevel(-1);
        }

        if (tokens.contains(TokenType.STORM_TOKEN)) {
            logger.info("Storm! The palisade level drops by 1!");
            MethodRepository.changePalisadeLevel(-1);
        }
        tokens.clear();
    }

    public Set<TokenType> getTokens() {
        return tokens;
    }

    public void setTokens(Set<TokenType> tokens) {
        this.tokens = tokens;
    }

    public boolean isRollBeastDiceWhileWeatherPhase() {
        return rollBeastDiceWhileWeatherPhase;
    }

    public void setRollBeastDiceWhileWeatherPhase(boolean rollBeastDiceWhileWeatherPhase) {
        this.rollBeastDiceWhileWeatherPhase = rollBeastDiceWhileWeatherPhase;
    }
}
