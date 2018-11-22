package model.phase;

import model.data.GlobalData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NightPhase implements Phase {
    private Logger logger = LogManager.getLogger(NightPhase.class);

    @Override
    public void initializePhase(GlobalData globalData) {
        logger.info("---> Phase: NIGHT");
    }

    @Override
    public void runPhase(GlobalData globalData) {

    }
}
