package model.elements.decks.tiles;

import model.enums.TerrainType;
import model.enums.elements.ResourceType;

public class IslandTile {
    private int tileID;
    private TerrainType terrainType;
    private ResourceType leftSquareResource;
    private ResourceType rightSquareResource;
    private int totemsNumber;
    private int discoveryTokensNumber;
    private boolean naturalShelter;

    public IslandTile(int tileID, TerrainType terrainType, ResourceType leftSquareResource, ResourceType rightSquareResource, int totemsNumber, int discoveryTokensNumber, boolean naturalShelter) {
        this.tileID = tileID;
        this.terrainType = terrainType;
        this.leftSquareResource = leftSquareResource;
        this.rightSquareResource = rightSquareResource;
        this.totemsNumber = totemsNumber;
        this.discoveryTokensNumber = discoveryTokensNumber;
        this.naturalShelter = naturalShelter;
    }

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public ResourceType getLeftSquareResource() {
        return leftSquareResource;
    }

    public void setLeftSquareResource(ResourceType leftSquareResource) {
        this.leftSquareResource = leftSquareResource;
    }

    public ResourceType getRightSquareResource() {
        return rightSquareResource;
    }

    public void setRightSquareResource(ResourceType rightSquareResource) {
        this.rightSquareResource = rightSquareResource;
    }

    public int getTotemsNumber() {
        return totemsNumber;
    }

    public void setTotemsNumber(int totemsNumber) {
        this.totemsNumber = totemsNumber;
    }

    public int getDiscoveryTokensNumber() {
        return discoveryTokensNumber;
    }

    public void setDiscoveryTokensNumber(int discoveryTokensNumber) {
        this.discoveryTokensNumber = discoveryTokensNumber;
    }

    public boolean isNaturalShelter() {
        return naturalShelter;
    }

    public void setNaturalShelter(boolean naturalShelter) {
        this.naturalShelter = naturalShelter;
    }
}
