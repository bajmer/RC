package model.elements.tiles;

import model.enums.TerrainType;
import model.enums.elements.ResourceType;
import model.enums.elements.TokenType;

import java.util.HashSet;
import java.util.Set;

public class IslandTile {
    private int tileID;
    private TerrainType terrain;
    private ResourceType leftSource;
    private ResourceType rightSource;
    private boolean totem;
    private int discoveryTokensNumber;
    private boolean naturalShelter;
    private Set<TokenType> tokens;
    private boolean endOfLeftSource;
    private boolean endOfRightSource;
    private boolean coveredTerrain;
    private boolean upsideDown; // TODO: 2018-12-12 reset i obsługa

    public IslandTile(int tileID, TerrainType terrain, ResourceType leftSource, ResourceType rightSource, int discoveryTokensNumber, boolean totem, boolean naturalShelter) {
        this.tileID = tileID;
        this.terrain = terrain;
        this.leftSource = leftSource;
        this.rightSource = rightSource;
        this.discoveryTokensNumber = discoveryTokensNumber;
        this.totem = totem;
        this.naturalShelter = naturalShelter;
        this.tokens = new HashSet<>();
        this.endOfLeftSource = false;
        this.endOfRightSource = false;
        this.coveredTerrain = false;
        this.upsideDown = false;
    }

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public void setTerrain(TerrainType terrain) {
        this.terrain = terrain;
    }

    public ResourceType getLeftSource() {
        return leftSource;
    }

    public void setLeftSource(ResourceType leftSource) {
        this.leftSource = leftSource;
    }

    public ResourceType getRightSource() {
        return rightSource;
    }

    public void setRightSource(ResourceType rightSource) {
        this.rightSource = rightSource;
    }

    public boolean isTotem() {
        return totem;
    }

    public void setTotem(boolean totem) {
        this.totem = totem;
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

    public Set<TokenType> getTokens() {
        return tokens;
    }

    public void setTokens(Set<TokenType> tokens) {
        this.tokens = tokens;
    }

    public boolean isEndOfLeftSource() {
        return endOfLeftSource;
    }

    public void setEndOfLeftSource(boolean endOfLeftSource) {
        this.endOfLeftSource = endOfLeftSource;
    }

    public boolean isEndOfRightSource() {
        return endOfRightSource;
    }

    public void setEndOfRightSource(boolean endOfRightSource) {
        this.endOfRightSource = endOfRightSource;
    }

    public boolean isCoveredTerrain() {
        return coveredTerrain;
    }

    public void setCoveredTerrain(boolean coveredTerrain) {
        this.coveredTerrain = coveredTerrain;
    }

    public boolean isUpsideDown() {
        return upsideDown;
    }

    public void setUpsideDown(boolean upsideDown) {
        this.upsideDown = upsideDown;
    }
}
