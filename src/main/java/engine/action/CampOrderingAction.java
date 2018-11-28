package engine.action;

import model.enums.ActionType;

public class CampOrderingAction extends Action {
	public CampOrderingAction(ActionType actionType) {
		super(actionType);
		super.setMultipleAction(true);
	}

	@Override
	public void performTheAction() {

	}
}
