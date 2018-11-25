package engine.action;

import model.elements.Marker;
import model.enums.ActionType;
import model.enums.elements.MarkerType;
import model.requirements.Requirements;

import java.util.ArrayList;
import java.util.List;

public abstract class Action {
    private ActionType actionType;
    private Requirements requirements;
    private List<MarkerType> acceptedMarkerTypes;
    private List<Marker> assignedMarkers;
    private boolean availableToPerform;

    public Action(ActionType actionType, Requirements requirements, List<MarkerType> acceptedMarkerTypes) {
        this.actionType = actionType;
        this.requirements = requirements;
        this.acceptedMarkerTypes = acceptedMarkerTypes;
        this.assignedMarkers = new ArrayList<>();
        this.availableToPerform = false;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }

    public List<MarkerType> getAcceptedMarkerTypes() {
        return acceptedMarkerTypes;
    }

    public void setAcceptedMarkerTypes(List<MarkerType> acceptedMarkerTypes) {
        this.acceptedMarkerTypes = acceptedMarkerTypes;
    }

    public List<Marker> getAssignedMarkers() {
        return assignedMarkers;
    }

    public void setAssignedMarkers(List<Marker> assignedMarkers) {
        this.assignedMarkers = assignedMarkers;
    }

    public boolean isAvailableToPerform() {
        return availableToPerform;
    }

    public void setAvailableToPerform(boolean availableToPerform) {
        this.availableToPerform = availableToPerform;
    }

    public void performTheAction() {

    }
}
