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
            String[] dicesString = dicesConfigString.split("-");
            Arrays.stream(dicesString).forEach(s -> dices.add(DiceType.valueOf(s)));
        } else {
            dices.add(DiceType.valueOf(dicesConfigString));
        }

        dices.stream().filter(diceType -> diceType != DiceType.NONE).forEach(diceType -> {
            DiceWallType result = globalData.getDices().roll(DicesGroupType.WEATHER_DICES, diceType);
            // TODO: 2018-11-22
        });


//
//        AtomicInteger rainCloudsNumber = new AtomicInteger();
//        AtomicInteger snowCloudsNumber = new AtomicInteger();
//        AtomicBoolean beastAttack = new AtomicBoolean(false);
//        AtomicBoolean palisadeDamage = new AtomicBoolean(false);
//        AtomicBoolean foodDiscard = new AtomicBoolean(false);
//
//        Mappings.getScenarioIdToRoundWeatherDicesMapMapping().get(scenario.getId()).get(scenario.getRound()).forEach(
//                diceType -> {
//                    DiceWallType result = dices.roll(dices.getDiceTypeToDiceMapping().get(diceType));
//                    logger.info("--->Rzut kością pogody " + diceType + ". Wynik: " + result);
//                    switch (result) {
//                        case SINGLE_RAIN:
//                            rainCloudsNumber.getAndIncrement();
//                            break;
//                        case DOUBLE_RAIN:
//                            rainCloudsNumber.getAndAdd(2);
//                            break;
//                        case SINGLE_SNOW:
//                            snowCloudsNumber.getAndIncrement();
//                            break;
//                        case DOUBLE_SNOW:
//                            snowCloudsNumber.getAndAdd(2);
//                            break;
//                        case BEAST_ATTACK:
//                            beastAttack.set(true);
//                            break;
//                        case PALISADE_DAMAGE:
//                            palisadeDamage.set(true);
//                            break;
//                        case FOOD_DISCARD:
//                            foodDiscard.set(true);
//                            break;
//                        case NOTHING:
//                            break;
//                    }
//                }
//        );
//
//        if (snowCloudsNumber.get() > 0) {
//            logger.debug("--->Liczba chmur ziomowych: " + snowCloudsNumber.get() + ". Za każdą chmurę należy odrzucić 1 drewno.");
//            GameInfo.changeWoodLevel(-snowCloudsNumber.get());
//        }
//
//        int cloudsSum = rainCloudsNumber.get() + snowCloudsNumber.get();
//        int missingRoofLevel = cloudsSum - GameInfo.getRoofLevel();
//        if (missingRoofLevel > 0) {
//            logger.debug("--->Liczba wszystkich chmur: " + cloudsSum + ". Za każdy brakujący poziom dachu należy odrzucić 1 drewno i 1 jedzenie.");
//            GameInfo.changeWoodLevel(-missingRoofLevel);
//            GameInfo.changeFoodLevel(-missingRoofLevel);
//        }
//
//        if (beastAttack.get()) {
//            logger.info("--->Atak bestii o sile 3!");
//        } else if (palisadeDamage.get()) {
//            logger.info("--->Poziom palisady spada o 1!");
//            GameInfo.changePalisadeLevel(-1);
//        } else if (foodDiscard.get()) {
//            logger.info("--->Należy odrzucić 1 jedzenie!");
//            GameInfo.changeFoodLevel(-1);
//        }

    }
}
