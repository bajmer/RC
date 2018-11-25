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
        globalData.changeFoodLevel(foodProduction);
        logger.info("The following amount of FOOD was received: " + foodProduction);

        int woodProduction = globalData.getGameParams().getWoodProduction();
        globalData.changeWoodLevel(woodProduction);
        logger.info("The following amount of WOOD was received: " + woodProduction);
    }

}
