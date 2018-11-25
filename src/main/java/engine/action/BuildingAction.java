package engine.action;

import model.enums.ActionType;
import model.enums.elements.MarkerType;
import model.requirements.Requirements;

import java.util.List;

public class BuildingAction extends Action {
    public BuildingAction(ActionType actionType, Requirements requirements, List<MarkerType> acceptedMarkerTypes) {
        super(actionType, requirements, acceptedMarkerTypes);
    }

    @Override
    public void performTheAction() {

    }
}