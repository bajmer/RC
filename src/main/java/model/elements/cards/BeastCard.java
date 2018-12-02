package model.elements.cards;

import model.enums.cards.BeastType;

public class BeastCard {
    private BeastType beast;
    private int strength;
    private int weaponLevelDecrease;
    private int foodAmount;
    private int furAmount;

    public BeastCard(BeastType beast, int strength, int weaponLevelDecrease, int foodAmount, int furAmount) {
        this.beast = beast;
        this.strength = strength;
        this.weaponLevelDecrease = weaponLevelDecrease;
        this.foodAmount = foodAmount;
        this.furAmount = furAmount;
    }

    public BeastType getBeast() {
        return beast;
    }

    public void setBeast(BeastType beast) {
        this.beast = beast;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getWeaponLevelDecrease() {
        return weaponLevelDecrease;
    }

    public void setWeaponLevelDecrease(int weaponLevelDecrease) {
        this.weaponLevelDecrease = weaponLevelDecrease;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
    }

    public int getFurAmount() {
        return furAmount;
    }

    public void setFurAmount(int furAmount) {
        this.furAmount = furAmount;
    }
}
