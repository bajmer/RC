package engine.phase;

import engine.MethodRepository;
import model.data.GlobalData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoralePhase implements Phase {
    private Logger logger = LogManager.getLogger(MoralePhase.class);

    @Override
    public void initializePhase() {
        if (GlobalData.getMainCharactersNumber() == 1) {
            MethodRepository.changeMoraleLevel(1);
        }

        logger.info("---> Phase: MORALE");
    }

    @Override
    public void runPhase() {
        int morale = GlobalData.getLevels().getMoraleLevel();
        logger.info("Morale level: " + morale);

        MethodRepository.changeDeterminationLevel(morale, GlobalData.getFirstPlayer());
    }

}
