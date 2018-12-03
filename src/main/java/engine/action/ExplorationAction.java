package engine.action;

import model.cards.AdventureCard;
import model.data.GlobalData;
import model.elements.Marker;
import model.elements.tiles.IslandTile;
import model.enums.ActionType;
import model.enums.elements.dices.DiceWallType;
import model.enums.elements.dices.DicesGroupType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
    public void performTheAction(GlobalData globalData) {
        StringBuilder sb = new StringBuilder();
        super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", "")).append(" "));
        logger.info("++ Action: EXPLORATION, Characters: " + sb.toString() + ", Position on board: " + positionOnBoard);

        int minMarkersNumber = 1; // TODO: 2018-11-30
        if (super.getAssignedMarkers().size() == minMarkersNumber) {
            List<DiceWallType> results = super.rollDices(globalData, DicesGroupType.EXPLORATION_DICES);
            Marker leaderMarker = super.getAssignedMarkers().get(0);
            if (results.contains(DiceWallType.WOUND)) {
                globalData.getCharacters().stream()
                        .filter(character -> character.getMarkers().contains(leaderMarker))
                        .findFirst().get().changeLivesLevel(-1);
            }
            if (results.contains(DiceWallType.SUCCESS)) {
                handleSuccess(globalData);
            } else if (results.contains(DiceWallType.DETERMINATION)) {
                globalData.getCharacters().stream()
                        .filter(character -> character.getMarkers().contains(leaderMarker))
                        .findFirst().get().changeDeterminationLevel(2);
            }
            if (results.contains(DiceWallType.ADVENTURE)) {
                globalData.getDecks().setTokenOnExplorationAdventureDeck(false);
                AdventureCard adventureCard = globalData.getDecks().getExplorationAdventureCardsDeck().removeFirst();
                adventureCard.handleAdventure();
            }
        } else {
            handleSuccess(globalData);
        }
        if (globalData.getDecks().isTokenOnExplorationAdventureDeck()) {
            globalData.getDecks().setTokenOnExplorationAdventureDeck(false);
            AdventureCard adventureCard = globalData.getDecks().getExplorationAdventureCardsDeck().removeFirst();
            adventureCard.handleAdventure();
        }
    }

    private void handleSuccess(GlobalData globalData) {
        IslandTile discoveredTile = globalData.getDecks().getIslandTilesStack().removeFirst();
        globalData.getDiscoveredIslandTiles().add(discoveredTile);
        globalData.getAvailableTerrainTypes().add(discoveredTile.getTerrainType());

        // TODO: 2018-11-22 Handle resources

        // TODO: 2018-11-22 Handle totems

        // TODO: 2018-11-22 Handle discovery tokens
    }
}
