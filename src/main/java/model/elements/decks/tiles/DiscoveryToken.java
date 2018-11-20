package model.elements.decks.tiles;

import model.enums.DiscoveryTokenType;

public class DiscoveryToken {
    private DiscoveryTokenType discoveryToken;

    public DiscoveryToken(DiscoveryTokenType discoveryToken) {
        this.discoveryToken = discoveryToken;
    }

    public DiscoveryTokenType getDiscoveryToken() {
        return discoveryToken;
    }

    public void setDiscoveryToken(DiscoveryTokenType discoveryToken) {
        this.discoveryToken = discoveryToken;
    }
}
