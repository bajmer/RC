package model.resources;

import model.elements.tiles.DiscoveryToken;

import java.util.ArrayList;
import java.util.List;

public class Resources {
    private int woodAmount;
    private int foodAmount;
    private int longTermFoodAmount;
    private int furAmount;
    private List<DiscoveryToken> discoveryTokens;

    public Resources() {
        this.woodAmount = 0;
        this.foodAmount = 0;
        this.longTermFoodAmount = 0;
        this.furAmount = 0;
        this.discoveryTokens = new ArrayList<>();
    }

    public int getWoodAmount() {
        return woodAmount;
    }

    public void setWoodAmount(int woodAmount) {
        this.woodAmount = woodAmount;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
    }

    public int getLongTermFoodAmount() {
        return longTermFoodAmount;
    }

    public void setLongTermFoodAmount(int longTermFoodAmount) {
        this.longTermFoodAmount = longTermFoodAmount;
    }

    public int getFurAmount() {
        return furAmount;
    }

    public void setFurAmount(int furAmount) {
        this.furAmount = furAmount;
    }

    public List<DiscoveryToken> getDiscoveryTokens() {
        return discoveryTokens;
    }

    public void setDiscoveryTokens(List<DiscoveryToken> discoveryTokens) {
        this.discoveryTokens = discoveryTokens;
    }
}
