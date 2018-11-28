package engine.action;

import model.enums.ActionType;
import model.enums.elements.ResourceType;

public class ResourcesAction extends Action {
	private ResourceType resourceType;
	private int positionOnBoard;

	public ResourcesAction(ActionType actionType, ResourceType resourceType, int positionOnBoard) {
		super(actionType);
		this.resourceType = resourceType;
		this.positionOnBoard = positionOnBoard;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public int getPositionOnBoard() {
		return positionOnBoard;
	}

	public void setPositionOnBoard(int positionOnBoard) {
		this.positionOnBoard = positionOnBoard;
	}

	@Override
	public void performTheAction() {

	}
}
