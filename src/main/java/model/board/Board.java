package model.board;

import model.elements.tiles.IslandTile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Map<Integer, IslandTile> tilePositionIdToIslandTile;
    private Map<IslandTile, Integer> islandTileToTilePosition;
    private Map<Integer, List<Integer>> tilesPositionsIdInNeighbourhood;
    private Map<List<Integer>, Integer> distancesBetweenTilesPositions;

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

        islandTileToTilePosition = new HashMap<>();

        tilesPositionsIdInNeighbourhood = new HashMap<>();
        tilesPositionsIdInNeighbourhood.put(1, Arrays.asList(2, 3, 4));
        tilesPositionsIdInNeighbourhood.put(2, Arrays.asList(1, 3, 5));
        tilesPositionsIdInNeighbourhood.put(3, Arrays.asList(1, 2, 4, 5, 6, 7));
        tilesPositionsIdInNeighbourhood.put(4, Arrays.asList(1, 3, 7));
        tilesPositionsIdInNeighbourhood.put(5, Arrays.asList(2, 3, 6, 8));
        tilesPositionsIdInNeighbourhood.put(6, Arrays.asList(3, 5, 7, 8, 9, 10));
        tilesPositionsIdInNeighbourhood.put(7, Arrays.asList(3, 4, 6, 10));
        tilesPositionsIdInNeighbourhood.put(8, Arrays.asList(5, 6, 9));
        tilesPositionsIdInNeighbourhood.put(9, Arrays.asList(6, 8, 10));
        tilesPositionsIdInNeighbourhood.put(10, Arrays.asList(6, 7, 9));

        distancesBetweenTilesPositions = new HashMap<>();
        distancesBetweenTilesPositions.put(Arrays.asList(1, 1), 0);
        distancesBetweenTilesPositions.put(Arrays.asList(1, 2), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(1, 3), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(1, 4), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(1, 5), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(1, 6), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(1, 7), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(1, 8), 3);
        distancesBetweenTilesPositions.put(Arrays.asList(1, 9), 3);
        distancesBetweenTilesPositions.put(Arrays.asList(1, 10), 3);
        distancesBetweenTilesPositions.put(Arrays.asList(2, 2), 0);
        distancesBetweenTilesPositions.put(Arrays.asList(2, 3), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(2, 4), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(2, 5), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(2, 6), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(2, 7), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(2, 8), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(2, 9), 3);
        distancesBetweenTilesPositions.put(Arrays.asList(2, 10), 3);
        distancesBetweenTilesPositions.put(Arrays.asList(3, 3), 0);
        distancesBetweenTilesPositions.put(Arrays.asList(3, 4), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(3, 5), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(3, 6), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(3, 7), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(3, 8), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(3, 9), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(3, 10), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(4, 4), 0);
        distancesBetweenTilesPositions.put(Arrays.asList(4, 5), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(4, 6), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(4, 7), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(4, 8), 3);
        distancesBetweenTilesPositions.put(Arrays.asList(4, 9), 3);
        distancesBetweenTilesPositions.put(Arrays.asList(4, 10), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(5, 5), 0);
        distancesBetweenTilesPositions.put(Arrays.asList(5, 6), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(5, 7), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(5, 8), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(5, 9), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(5, 10), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(6, 6), 0);
        distancesBetweenTilesPositions.put(Arrays.asList(6, 7), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(6, 8), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(6, 9), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(6, 10), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(7, 7), 0);
        distancesBetweenTilesPositions.put(Arrays.asList(7, 8), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(7, 9), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(7, 10), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(8, 8), 0);
        distancesBetweenTilesPositions.put(Arrays.asList(8, 9), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(8, 10), 2);
        distancesBetweenTilesPositions.put(Arrays.asList(9, 9), 0);
        distancesBetweenTilesPositions.put(Arrays.asList(9, 10), 1);
        distancesBetweenTilesPositions.put(Arrays.asList(10, 10), 0);
    }

    public Map<Integer, IslandTile> getTilePositionIdToIslandTile() {
        return tilePositionIdToIslandTile;
    }

    public void setTilePositionIdToIslandTile(Map<Integer, IslandTile> tilePositionIdToIslandTile) {
        this.tilePositionIdToIslandTile = tilePositionIdToIslandTile;
    }

    public Map<IslandTile, Integer> getIslandTileToTilePosition() {
        return islandTileToTilePosition;
    }

    public void setIslandTileToTilePosition(Map<IslandTile, Integer> islandTileToTilePosition) {
        this.islandTileToTilePosition = islandTileToTilePosition;
    }

    public Map<Integer, List<Integer>> getTilesPositionsIdInNeighbourhood() {
        return tilesPositionsIdInNeighbourhood;
    }

    public void setTilesPositionsIdInNeighbourhood(Map<Integer, List<Integer>> tilesPositionsIdInNeighbourhood) {
        this.tilesPositionsIdInNeighbourhood = tilesPositionsIdInNeighbourhood;
    }

    public Map<List<Integer>, Integer> getDistancesBetweenTilesPositions() {
        return distancesBetweenTilesPositions;
    }

    public void setDistancesBetweenTilesPositions(Map<List<Integer>, Integer> distancesBetweenTilesPositions) {
        this.distancesBetweenTilesPositions = distancesBetweenTilesPositions;
    }
}
