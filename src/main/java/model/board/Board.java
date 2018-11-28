package model.board;

import model.elements.tiles.IslandTile;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Integer, IslandTile> tilePositionIdToIslandTile;

    public Board() {
        tilePositionIdToIslandTile = new HashMap<>();
        tilePositionIdToIslandTile.put(1, null);
        tilePositionIdToIslandTile.put(2, null);
        tilePositionIdToIslandTile.put(3, null);
        tilePositionIdToIslandTile.put(4, null);
        tilePositionIdToIslandTile.put(5, null);
        tilePositionIdToIslandTile.put(6, null);
        tilePositionIdToIslandTile.put(7, null);
        tilePositionIdToIslandTile.put(8, null);
        tilePositionIdToIslandTile.put(9, null);
        tilePositionIdToIslandTile.put(10, null);
    }

    public Map<Integer, IslandTile> getTilePositionIdToIslandTile() {
        return tilePositionIdToIslandTile;
    }

    public void setTilePositionIdToIslandTile(Map<Integer, IslandTile> tilePositionIdToIslandTile) {
        this.tilePositionIdToIslandTile = tilePositionIdToIslandTile;
    }
}
