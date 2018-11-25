package model.phase;

import model.character.main.MainCharacter;
import model.data.GlobalData;
import model.elements.cards.mystery.MysteryTreasureCard;
import model.elements.tiles.IslandTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

import static model.enums.cards.IdeaType.CELLAR;
import static model.enums.cards.mystery.MysteryTreasureType.BARREL;
import static model.enums.cards.mystery.MysteryTreasureType.BOXES;

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
            // TODO: 2018-11-23 Handle choosing starving characters
            globalData.changeFoodLevel(-allFoodAmount);
        } else {
            globalData.changeFoodLevel(-requiredFood);
        }

        // TODO: 2018-11-23 Handle option of moving the camp

        IslandTile camp = globalData.getCamp();
        if (!globalData.isShelter() && !camp.isNaturalShelter()) {
            logger.info("No shelter! Everyone is spending the night under the open sky!");
            globalData.getCharacters().stream()
                    .filter(character -> character instanceof MainCharacter)
                    .forEach(character -> character.changeLivesLevel(-1));
        }

        boolean canStorageFood = globalData.getInventions().stream()
                .anyMatch(invention -> Arrays.asList(CELLAR/*todo Other inventions*/).contains(invention.getIdea()));
        canStorageFood |= globalData.getTreasures().stream()
                .filter(mysteryCard -> mysteryCard instanceof MysteryTreasureCard)
                .map(MysteryTreasureCard.class::cast)
                .anyMatch(treasure -> Arrays.asList(BARREL, BOXES).contains(treasure.getTreasureType()));

        if (!canStorageFood) {
            logger.info("You cannot store food. All fresh food has broken.");
            globalData.getAvailableResources().setFoodAmount(0);
        }
    }
}
