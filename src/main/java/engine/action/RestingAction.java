package engine.action;

import model.data.GlobalData;
import model.enums.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestingAction extends Action {

    private Logger logger = LogManager.getLogger(RestingAction.class);

	public RestingAction(ActionType actionType) {
		super(actionType);
		super.setMultipleAction(true);
	}

	@Override
    public void performTheAction(GlobalData globalData) {
        StringBuilder sb = new StringBuilder();
        super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", "")).append(" "));
        logger.info("++ Action: REST, Characters: " + sb.toString());
        super.getAssignedMarkers().forEach(marker -> globalData.getCharacters().stream()
                .filter(character -> character.getMarkers().contains(marker))
                .findFirst().get().changeLivesLevel(1));
	}
}
