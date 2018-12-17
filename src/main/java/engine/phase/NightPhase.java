package engine.phase;

import engine.MethodRepository;
import model.character.MainCharacter;
import model.data.GlobalData;
import model.elements.tiles.IslandTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.enums.cards.IdeaType.CELLAR;
import static model.enums.cards.MysteryType.TREAS_BECZKA;
import static model.enums.cards.MysteryType.TREAS_SKRZYNIE;

public class NightPhase implements Phase {
    private static boolean everyoneNeedAdditionalFood = false;
    private Logger logger = LogManager.getLogger(NightPhase.class);

    public static boolean isEveryoneNeedAdditionalFood() {
        return everyoneNeedAdditionalFood;
    }

    public static void setEveryoneNeedAdditionalFood(boolean everyoneNeedAdditionalFood) {
        NightPhase.everyoneNeedAdditionalFood = everyoneNeedAdditionalFood;
    }

    @Override
    public void initializePhase() {
        logger.info("---> Phase: NIGHT");
    }

    @Override
    public void runPhase() {
        int requiredFood = GlobalData.getMainCharactersNumber();
        int foodAmount = GlobalData.getAvailableResources().getFoodAmount();
        int ltFoodAmount = GlobalData.getAvailableResources().getLongTermFoodAmount();
        int allFoodAmount = foodAmount + ltFoodAmount;

        if (requiredFood > allFoodAmount) {
            MethodRepository.changeFoodLevel(-allFoodAmount);
            List<MainCharacter> starvingCharacters = new ArrayList<>(); // TODO: ... = chooseStarvingCharacters(requiredFood - allFoodAmount);
            starvingCharacters.forEach(character -> {
                MethodRepository.changeLivesLevel(-2, character);
            });
        } else {
            MethodRepository.changeFoodLevel(-requiredFood);
        }

        if (everyoneNeedAdditionalFood) {
            // TODO: 2018-12-11
        }

        // TODO: 2018-11-23 Handle option of moving the camp

        IslandTile camp = GlobalData.getCamp();
        if (!GlobalData.isShelter() && !camp.isNaturalShelter()) {
            logger.info("No shelter! Everyone is spending the night under the open sky!");
            MethodRepository.decreaseLivesOfAllMainCharacters(-1);
        }

        boolean canStorageFood = GlobalData.getInventions().stream()
                .anyMatch(invention -> Arrays.asList(CELLAR/*todo Other inventions*/).contains(invention.getIdea()));
        canStorageFood |= GlobalData.getTreasures().stream()
                .anyMatch(treasure -> Arrays.asList(TREAS_BECZKA, TREAS_SKRZYNIE).contains(treasure.getMystery()));

        if (!canStorageFood) {
            logger.info("You cannot store food. All fresh food has broken.");
            GlobalData.getAvailableResources().setFoodAmount(0);
        }
    }
}
