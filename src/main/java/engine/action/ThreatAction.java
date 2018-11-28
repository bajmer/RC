package engine.action;

import model.elements.cards.EventCard;
import model.enums.ActionType;
import model.requirements.Requirements;

public class ThreatAction extends Action {

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
    public void performTheAction() {

    }
}
