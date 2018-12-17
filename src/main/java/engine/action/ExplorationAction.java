package engine.action;

import engine.MethodRepository;
import model.character.Character;
import model.enums.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExplorationAction extends Action {
    private Logger logger = LogManager.getLogger(ExplorationAction.class);

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
    public void performTheAction(Character leader) {
        if (true /*zwykła eksploracja*/) {
            MethodRepository.handleDiscoveryOfNewIslandTile(positionOnBoard);
        } else {
            // TODO: 2018-12-13 sepcjalna eksploracja
        }
    }
}
