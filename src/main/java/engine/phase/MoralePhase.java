package engine.phase;

import model.data.GlobalData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoralePhase implements Phase {
    private Logger logger = LogManager.getLogger(MoralePhase.class);

    @Override
    public void initializePhase(GlobalData globalData) {
        logger.info("---> Phase: MORALE");
    }

    @Override
    public void runPhase(GlobalData globalData) {
        int morale = globalData.getGameParams().getMoraleLevel();
        logger.info("Morale level: " + morale);

        globalData.getFirstPlayer().changeDeterminationLevel(morale);
        globalData.decreaseMoraleAfterReducingCharacterLives(morale, globalData.getFirstPlayer());
    }

}
