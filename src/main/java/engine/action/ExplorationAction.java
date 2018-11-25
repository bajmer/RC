package engine.action;

import model.enums.ActionType;
import model.enums.elements.MarkerType;
import model.requirements.Requirements;

import java.util.List;

public class ExplorationAction extends Action {
    public ExplorationAction(ActionType actionType, Requirements requirements, List<MarkerType> acceptedMarkerTypes) {
        super(actionType, requirements, acceptedMarkerTypes);
    }

    @Override
    public void performTheAction() {

    }
}
