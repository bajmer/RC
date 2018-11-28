package engine.action;

import model.enums.ActionType;

public class ExplorationAction extends Action {

    private int positionOnBoard;

    public ExplorationAction(ActionType actionType, int positionOnBoard) {
        super(actionType);
        this.positionOnBoard = positionOnBoard;
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
