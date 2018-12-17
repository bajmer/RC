package engine.action;

import engine.MethodRepository;
import model.character.Character;
import model.data.GlobalData;
import model.elements.Marker;
import model.enums.ActionType;
import model.enums.elements.MarkerType;
import model.enums.elements.dices.DiceType;
import model.enums.elements.dices.DiceWallType;
import model.enums.elements.dices.DicesGroupType;
import model.requirements.Requirements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.enums.elements.MarkerType.*;

public abstract class Action {
	private static boolean acceptedMarkersChangedForFirstPlayer = false; // TODO: 2018-12-12 reset
	private Logger logger = LogManager.getLogger(Action.class);
	private ActionType actionType;
	private Requirements requirements;
	private List<MarkerType> acceptedMarkerTypes = Arrays.asList(CARPENTER_MARKER, COOK_MARKER, EXPLORER_MARKER, SOLDIER_MARKER, FRIDAY_MARKER);
	private List<Marker> assignedMarkers;
	private boolean multipleAction;
	private boolean possibleToPerform;
	private boolean chosen;
	private int orderOfExecution;
	private int minAssignedMarkersNumber;

	public Action(ActionType actionType) {
		this.actionType = actionType;
		this.requirements = new Requirements();
		this.assignedMarkers = new ArrayList<>();
		this.possibleToPerform = false;
		this.multipleAction = false;
		this.chosen = false;
		this.minAssignedMarkersNumber = 0;
		if (actionType == ActionType.THREAT_ACTION) {
			this.orderOfExecution = 1;
		} else if (actionType == ActionType.HUNTING_ACTION) {
			this.acceptedMarkerTypes.addAll(Arrays.asList(DOG_HELPER_MARKER, HUNTER_HELPER_MARKER));
			this.orderOfExecution = 2;
		} else if (actionType == ActionType.BUILDING_ACTION) {
			this.acceptedMarkerTypes.add(BUILDER_HELPER_MARKER);
			this.orderOfExecution = 3;
		} else if (actionType == ActionType.RESOURCES_ACTION) {
			this.acceptedMarkerTypes.add(COLLECTOR_HELPER_MARKER);
			this.orderOfExecution = 4;
		} else if (actionType == ActionType.EXPLORATION_ACTION) {
			this.acceptedMarkerTypes.addAll(Arrays.asList(DOG_HELPER_MARKER, EXPLORER_HELPER_MARKER));
			this.orderOfExecution = 5;
		} else if (actionType == ActionType.CAMP_ACTION) {
			this.orderOfExecution = 6;
		} else if (actionType == ActionType.REST_ACTION) {
			this.orderOfExecution = 7;
		}

		if (acceptedMarkersChangedForFirstPlayer) {
			Character character = GlobalData.getFirstPlayer();
			if (actionType == ActionType.THREAT_ACTION || actionType == ActionType.HUNTING_ACTION || actionType == ActionType.RESOURCES_ACTION || actionType == ActionType.EXPLORATION_ACTION) {
				acceptedMarkerTypes.remove(character.getMarkers().get(0).getMarkerType());
			}
		}
	}

	public static boolean isAcceptedMarkersChangedForFirstPlayer() {
		return acceptedMarkersChangedForFirstPlayer;
	}

	public static void setAcceptedMarkersChangedForFirstPlayer(boolean acceptedMarkersChangedForFirstPlayer) {
		Action.acceptedMarkersChangedForFirstPlayer = acceptedMarkersChangedForFirstPlayer;
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

	public int getMinAssignedMarkersNumber() {
		return minAssignedMarkersNumber;
	}

	public void setMinAssignedMarkersNumber(int minAssignedMarkersNumber) {
		this.minAssignedMarkersNumber = minAssignedMarkersNumber;
	}

	public void performTheAction(Character leader) {

	}

	public List<DiceWallType> rollDices(DicesGroupType dicesGroupType, boolean rollAgainIfSuccess) {
		List<DiceWallType> results = new ArrayList<>();
		DiceWallType successResult = MethodRepository.roll(dicesGroupType, DiceType.SUCCESS);
		if (rollAgainIfSuccess) {
			successResult = MethodRepository.roll(dicesGroupType, DiceType.SUCCESS);
		}
		results.add(successResult);
		results.add(MethodRepository.roll(dicesGroupType, DiceType.ADVENTURE));
		results.add(MethodRepository.roll(dicesGroupType, DiceType.WOUND));

		StringBuilder sb = new StringBuilder();
		results.forEach(result -> sb.append(result.toString()).append(" "));
		String adventureType = null;
		if (dicesGroupType == DicesGroupType.BUILDING_DICES) {
			adventureType = "BUILDING";
		} else if (dicesGroupType == DicesGroupType.RESOURCES_DICES) {
			adventureType = "RESOURCE";
		} else if (dicesGroupType == DicesGroupType.EXPLORATION_DICES) {
			adventureType = "EXPLORATION";
		}
		logger.info(adventureType + " DICES have been rolled. Results: " + sb.toString());
		return results;
	}
}
