package engine.action;

import model.data.GlobalData;
import model.elements.cards.EventCard;
import model.enums.ActionType;
import model.requirements.Requirements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreatAction extends Action {
	private Logger logger = LogManager.getLogger(ThreatAction.class);

	private EventCard eventCard;

	public ThreatAction(ActionType actionType, EventCard eventCard) {
		super(actionType);
		this.eventCard = eventCard;
		super.setRequirements(createRequirements());
	}

	private Requirements createRequirements() {
		Requirements requirements;

		// TODO: 2018-11-27
		switch (eventCard.getThreatAction()) {
			case SOME_ACTION:
				requirements = new Requirements();
				break;
			case NONE:
				requirements = new Requirements();
				break;
			default:
				requirements = new Requirements();
		}
		return requirements;
	}

	@Override
	public void performTheAction(GlobalData globalData) {
		StringBuilder sb = new StringBuilder();
		super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", "")).append(" "));
		logger.info("++ Action: THREAT, Characters: " + sb.toString() + ", Threat type: " + eventCard.getThreatAction().toString());
		switch (eventCard.getThreatAction()) {
			case SOME_ACTION:
				break;
			case NONE:
				break;
			default:
				break;
		}
	}
}
