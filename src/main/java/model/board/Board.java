package model.board;

import model.elements.tiles.IslandTile;

import java.util.Map;

public class Board {
    private Map<Integer, IslandTile> tilePositionIdToIslandTile;

    public Board(Map<Integer, IslandTile> tilePositionIdToIslandTile) {
        this.tilePositionIdToIslandTile = tilePositionIdToIslandTile;
    }

    public Map<Integer, IslandTile> getTilePositionIdToIslandTile() {
        return tilePositionIdToIslandTile;
    }

    public void setTilePositionIdToIslandTile(Map<Integer, IslandTile> tilePositionIdToIslandTile) {
        this.tilePositionIdToIslandTile = tilePositionIdToIslandTile;
    }
}
