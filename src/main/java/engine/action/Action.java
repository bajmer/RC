package engine.action;

import model.elements.Marker;
import model.enums.ActionType;
import model.enums.elements.MarkerType;
import model.requirements.Requirements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.enums.elements.MarkerType.*;

public abstract class Action {
    private ActionType actionType;
    private Requirements requirements;
    private List<MarkerType> acceptedMarkerTypes = new ArrayList<>(Arrays.asList(CARPENTER_MARKER, COOK_MARKER, EXPLORER_MARKER, SOLDIER_MARKER, FRIDAY_MARKER));
    private List<Marker> assignedMarkers;
    private boolean multipleAction;
    private boolean possibleToPerform;
    private boolean chosen;
    private int orderOfExecution;

    public Action(ActionType actionType) {
        this.actionType = actionType;
        this.requirements = new Requirements();
        this.assignedMarkers = new ArrayList<>();
        this.possibleToPerform = false;
        this.multipleAction = false;
        this.chosen = false;
        if (actionType == ActionType.THREAT_ACTION) {
            this.orderOfExecution = 1;
        } else if (actionType == ActionType.HUNTING_ACTION) {
            this.acceptedMarkerTypes.addAll(Arrays.asList(DOG_MARKER, HUNTER_EXTRA_MARKER));
            this.orderOfExecution = 2;
        } else if (actionType == ActionType.BUILDING_ACTION) {
            this.acceptedMarkerTypes.add(BUILDER_EXTRA_MARKER);
            this.orderOfExecution = 3;
        } else if (actionType == ActionType.RESOURCES_ACTION) {
            this.acceptedMarkerTypes.add(COLLECTOR_EXTRA_MARKER);
            this.orderOfExecution = 4;
        } else if (actionType == ActionType.EXPLORATION_ACTION) {
            this.acceptedMarkerTypes.addAll(Arrays.asList(DOG_MARKER, EXPLORER_EXTRA_MARKER));
            this.orderOfExecution = 5;
        } else if (actionType == ActionType.CAMP_ACTION) {
            this.orderOfExecution = 6;
        } else if (actionType == ActionType.REST_ACTION) {
            this.orderOfExecution = 7;
        }
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

    public boolean isMultipleAction() {
        return multipleAction;
    }

    public void setMultipleAction(boolean multipleAction) {
        this.multipleAction = multipleAction;
    }

    public boolean isPossibleToPerform() {
        return possibleToPerform;
    }

    public void setPossibleToPerform(boolean possibleToPerform) {
        this.possibleToPerform = possibleToPerform;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public int getOrderOfExecution() {
        return orderOfExecution;
    }

    public void setOrderOfExecution(int orderOfExecution) {
        this.orderOfExecution = orderOfExecution;
    }

    public void performTheAction() {

    }
}
