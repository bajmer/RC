package model.data;

public class GameParams {
    private int moraleLevel;
    private int roofLevel;
    private int palisadeLevel;
    private int weaponLevel;
    private int foodProduction;
    private int woodProduction;

    public GameParams() {
        this.moraleLevel = 0;
        this.roofLevel = 0;
        this.palisadeLevel = 0;
        this.weaponLevel = 0;
        this.foodProduction = 0;
        this.woodProduction = 0;
    }

    public int getMoraleLevel() {
        return moraleLevel;
    }

    public void setMoraleLevel(int moraleLevel) {
        this.moraleLevel = moraleLevel;
    }

    public int getRoofLevel() {
        return roofLevel;
    }

    public void setRoofLevel(int roofLevel) {
        this.roofLevel = roofLevel;
    }

    public int getPalisadeLevel() {
        return palisadeLevel;
    }

    public void setPalisadeLevel(int palisadeLevel) {
        this.palisadeLevel = palisadeLevel;
    }

    public int getWeaponLevel() {
        return weaponLevel;
    }

    public void setWeaponLevel(int weaponLevel) {
        this.weaponLevel = weaponLevel;
    }

    public int getFoodProduction() {
        return foodProduction;
    }

    public void setFoodProduction(int foodProduction) {
        this.foodProduction = foodProduction;
    }

    public int getWoodProduction() {
        return woodProduction;
    }

    public void setWoodProduction(int woodProduction) {
        this.woodProduction = woodProduction;
    }
}
