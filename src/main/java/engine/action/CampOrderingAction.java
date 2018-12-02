package engine.action;

import model.character.main.MainCharacter;
import model.data.GlobalData;
import model.enums.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CampOrderingAction extends Action {

    private Logger logger = LogManager.getLogger(CampOrderingAction.class);

	public CampOrderingAction(ActionType actionType) {
		super(actionType);
		super.setMultipleAction(true);
	}

	@Override
    public void performTheAction(GlobalData globalData) {
        StringBuilder sb = new StringBuilder();
        super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", "")).append(" "));
        logger.info("++ Action: CAMP ORDERING, Characters: " + sb.toString());

        int charactersNumber = Math.toIntExact(globalData.getCharacters().stream()
                .filter(character -> character instanceof MainCharacter)
                .count());

        if (charactersNumber < 4) {
            super.getAssignedMarkers().forEach(marker -> globalData.getCharacters().stream()
                    .filter(character -> character.getMarkers().contains(marker))
                    .findFirst().get().changeDeterminationLevel(2));
            globalData.changeMoraleLevel(1);
        } else {
            boolean determination = true; // TODO: 2018-11-29 Choose determination or morale
            if (determination) {
                super.getAssignedMarkers().forEach(marker -> globalData.getCharacters().stream()
                        .filter(character -> character.getMarkers().contains(marker))
                        .findFirst().get().changeDeterminationLevel(2));
            } else {
                globalData.changeMoraleLevel(1);
            }
        }
	}
}
