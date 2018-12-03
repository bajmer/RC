package engine.action;

import model.cards.EventCard;
import model.data.GlobalData;
import model.enums.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreatAction extends Action {
	private Logger logger = LogManager.getLogger(ThreatAction.class);

	private EventCard eventCard;

	public ThreatAction(ActionType actionType, EventCard eventCard) {
		super(actionType);
		this.eventCard = eventCard;
		super.setRequirements(eventCard.specifyRequirements());
	}

	@Override
	public void performTheAction(GlobalData globalData) {
		StringBuilder sb = new StringBuilder();
		super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", "")).append(" "));
		logger.info("++ Action: THREAT, Characters: " + sb.toString() + ", Threat type: " + eventCard.getThreat().toString());

		eventCard.handleThreatAction();
	}
}
