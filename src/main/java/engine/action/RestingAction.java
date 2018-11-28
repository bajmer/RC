package engine.action;

import model.enums.ActionType;

public class RestingAction extends Action {
	public RestingAction(ActionType actionType) {
		super(actionType);
		super.setMultipleAction(true);
	}

	@Override
	public void performTheAction() {

	}
}
