package engine.phase;

import engine.MethodRepository;
import model.data.GlobalData;
import model.elements.tiles.IslandTile;
import model.enums.elements.ResourceType;
import model.enums.elements.TokenType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ProductionPhase implements Phase {
    private Logger logger = LogManager.getLogger(ProductionPhase.class);

    private static boolean noWood = false;
    private static boolean noFood = false;
    private static boolean woodPlusOne = false;
    private static boolean foodPlusOne = false;
    private static boolean halfProduction = false;

    public static boolean isNoWood() {
        return noWood;
    }

    public static void setNoWood(boolean noWood) {
        ProductionPhase.noWood = noWood;
    }

    public static boolean isNoFood() {
        return noFood;
    }

    public static void setNoFood(boolean noFood) {
        ProductionPhase.noFood = noFood;
    }

    public static boolean isWoodPlusOne() {
        return woodPlusOne;
    }

    public static void setWoodPlusOne(boolean woodPlusOne) {
        ProductionPhase.woodPlusOne = woodPlusOne;
    }

    public static boolean isFoodPlusOne() {
        return foodPlusOne;
    }

    public static void setFoodPlusOne(boolean foodPlusOne) {
        ProductionPhase.foodPlusOne = foodPlusOne;
    }

    public static boolean isHalfProduction() {
        return halfProduction;
    }

    public static void setHalfProduction(boolean halfProduction) {
        ProductionPhase.halfProduction = halfProduction;
    }

    @Override
    public void initializePhase() {
        logger.info("---> Phase: PRODUCTION");
    }

    @Override
    public void runPhase() {
        IslandTile camp = GlobalData.getCamp();
        Optional<IslandTile> shortcut = GlobalData.getDiscoveredIslandTiles().stream()
                .filter(islandTile -> islandTile.getTokens().contains(TokenType.SHORTCUT_TOKEN))
                .findFirst();

        int woodProduction = 0;
        if (!noWood) {
            woodProduction += camp.getLeftSource() == ResourceType.WOOD && !camp.isEndOfLeftSource() ? 1 : 0;
            woodProduction += camp.getRightSource() == ResourceType.WOOD && !camp.isEndOfRightSource() ? 1 : 0;
            woodProduction += camp.getTokens().contains(TokenType.ONE_MORE_WOOD_TOKEN) ? 1 : 0;
            if (shortcut.isPresent()) {
                woodProduction += shortcut.get().getLeftSource() == ResourceType.WOOD && !shortcut.get().isEndOfLeftSource() ? 1 : 0;
                woodProduction += shortcut.get().getRightSource() == ResourceType.WOOD && !shortcut.get().isEndOfRightSource() ? 1 : 0;
                woodProduction += shortcut.get().getTokens().contains(TokenType.ONE_MORE_WOOD_TOKEN) ? 1 : 0;
            }
            woodProduction += woodPlusOne ? 1 : 0;
        }

        int foodProduction = 0;
        if (!noFood) {
            foodProduction += camp.getLeftSource().toString().startsWith("FOOD_") && !camp.isEndOfLeftSource() ? 1 : 0;
            foodProduction += camp.getRightSource().toString().startsWith("FOOD_") && !camp.isEndOfRightSource() ? 1 : 0;
            foodProduction += camp.getTokens().contains(TokenType.ONE_MORE_FOOD_TOKEN) ? 1 : 0;
            if (shortcut.isPresent()) {
                foodProduction += shortcut.get().getLeftSource().toString().startsWith("FOOD_") && !shortcut.get().isEndOfLeftSource() ? 1 : 0;
                foodProduction += shortcut.get().getRightSource().toString().startsWith("FOOD_") && !shortcut.get().isEndOfRightSource() ? 1 : 0;
                foodProduction += shortcut.get().getTokens().contains(TokenType.ONE_MORE_FOOD_TOKEN) ? 1 : 0;
            }
            foodProduction += foodPlusOne ? 1 : 0;
        }

        if (halfProduction) {
            woodProduction = (int) Math.floor(woodProduction / 2);
            foodProduction = (int) Math.floor(foodProduction / 2);
        }

        logger.info("The following amount of FOOD was received: " + foodProduction);
        MethodRepository.changeFoodLevel(foodProduction);
        logger.info("The following amount of WOOD was received: " + woodProduction);
        MethodRepository.changeWoodLevel(woodProduction);

        noWood = false;
        noFood = false;
        woodPlusOne = false;
        foodPlusOne = false;
        halfProduction = false;
    }
}
