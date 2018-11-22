package model.resources;

public class Resources {
    private int woodAmount;
    private int foodAmount;
    private int longTermFoodAmount;
    private int hideAmount;

    public Resources() {
        this.woodAmount = 0;
        this.foodAmount = 0;
        this.longTermFoodAmount = 0;
        this.hideAmount = 0;
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

    public int getHideAmount() {
        return hideAmount;
    }

    public void setHideAmount(int hideAmount) {
        this.hideAmount = hideAmount;
    }
}
