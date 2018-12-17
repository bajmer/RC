package engine.action;

import controller.GameWindowController;
import engine.MethodRepository;
import model.character.Character;
import model.data.GlobalData;
import model.enums.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CampOrderingAction extends Action {

    private static boolean blackFlag = false;
    private Logger logger = LogManager.getLogger(CampOrderingAction.class);

	public CampOrderingAction(ActionType actionType) {
		super(actionType);
		super.setMultipleAction(true);
	}

    public static boolean isBlackFlag() {
        return blackFlag;
    }

    public static void setBlackFlag(boolean blackFlag) {
        CampOrderingAction.blackFlag = blackFlag;
    }

	@Override
    public void performTheAction(Character leader) {
        StringBuilder sb = new StringBuilder();
        super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", "")).append(" "));
        logger.info("++ Action: CAMP ORDERING, Characters: " + sb.toString());

        if (blackFlag) {
            blackFlag = false;
            return;
        }

        int charactersNumber = GlobalData.getMainCharactersNumber();
        if (charactersNumber < 4) {
            MethodRepository.changeDeterminationLevel(2, leader);
            MethodRepository.changeMoraleLevel(1);
        } else {
            String choice = GameWindowController.chooseDeterminationOrMorale();
            if (choice.equals("DETERMINATION")) {
                MethodRepository.changeDeterminationLevel(2, leader);
            } else if (choice.equals("MORALE")) {
                MethodRepository.changeMoraleLevel(1);
            }
        }
	}
}
