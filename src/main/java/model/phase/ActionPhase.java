package model.phase;

import model.data.GlobalData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionPhase implements Phase {
    private Logger logger = LogManager.getLogger(ActionPhase.class);

    @Override
    public void initializePhase(GlobalData globalData) {
        logger.info("---> Phase: ACTION");
    }

    @Override
    public void runPhase(GlobalData globalData) {

    }
}
