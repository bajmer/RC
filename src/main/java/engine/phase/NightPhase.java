package engine.phase;

import model.character.MainCharacter;
import model.data.GlobalData;
import model.elements.tiles.IslandTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.enums.cards.IdeaType.CELLAR;
import static model.enums.cards.MysteryType.TREAS_BARREL;
import static model.enums.cards.MysteryType.TREAS_BOXES;

public class NightPhase implements Phase {
    private Logger logger = LogManager.getLogger(NightPhase.class);

    @Override
    public void initializePhase(GlobalData globalData) {
        logger.info("---> Phase: NIGHT");
    }

    @Override
    public void runPhase(GlobalData globalData) {
        int requiredFood = Math.toIntExact(globalData.getCharacters().stream().filter(character -> character instanceof MainCharacter).count());
        int foodAmount = globalData.getAvailableResources().getFoodAmount();
        int ltFoodAmount = globalData.getAvailableResources().getLongTermFoodAmount();
        int allFoodAmount = foodAmount + ltFoodAmount;

        if (requiredFood > allFoodAmount) {
            globalData.changeFoodLevel(-allFoodAmount);
            List<MainCharacter> starvingCharacters = new ArrayList<>(); // TODO: ... = chooseStarvingCharacters(requiredFood - allFoodAmount);
            starvingCharacters.forEach(character -> {
                character.changeLivesLevel(-2);
                globalData.decreaseMoraleAfterReducingCharacterLives(-2, character);
            });
        } else {
            globalData.changeFoodLevel(-requiredFood);
        }

        // TODO: 2018-11-23 Handle option of moving the camp

        IslandTile camp = globalData.getCamp();
        if (!globalData.isShelter() && !camp.isNaturalShelter()) {
            logger.info("No shelter! Everyone is spending the night under the open sky!");
            globalData.reduceLivesLevelOfAllMainCharacters(-1);
        }

        boolean canStorageFood = globalData.getInventions().stream()
                .anyMatch(invention -> Arrays.asList(CELLAR/*todo Other inventions*/).contains(invention.getIdea()));
        canStorageFood |= globalData.getTreasures().stream()
                .anyMatch(treasure -> Arrays.asList(TREAS_BARREL, TREAS_BOXES).contains(treasure.getMystery()));

        if (!canStorageFood) {
            logger.info("You cannot store food. All fresh food has broken.");
            globalData.getAvailableResources().setFoodAmount(0);
        }
    }
}
