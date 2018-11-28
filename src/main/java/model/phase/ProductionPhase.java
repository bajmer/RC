package model.phase;

import model.data.GlobalData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductionPhase implements Phase {
    private Logger logger = LogManager.getLogger(ProductionPhase.class);

    @Override
    public void initializePhase(GlobalData globalData) {
        logger.info("---> Phase: PRODUCTION");
    }

    @Override
    public void runPhase(GlobalData globalData) {
        int foodProduction = globalData.getGameParams().getFoodProduction();
        logger.info("The following amount of FOOD was received: " + foodProduction);
        globalData.changeFoodLevel(foodProduction);

        int woodProduction = globalData.getGameParams().getWoodProduction();
        logger.info("The following amount of WOOD was received: " + woodProduction);
        globalData.changeWoodLevel(woodProduction);
    }

}
