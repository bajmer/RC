package engine.action;

import model.enums.ActionType;
import model.enums.elements.MarkerType;
import model.requirements.Requirements;

import java.util.List;

public class CampOrderingAction extends Action {
    public CampOrderingAction(ActionType actionType, Requirements requirements, List<MarkerType> acceptedMarkerTypes) {
        super(actionType, requirements, acceptedMarkerTypes);
    }

    @Override
    public void performTheAction() {

    }
}
