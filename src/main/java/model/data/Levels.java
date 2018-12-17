package model.data;

public class Levels {
    private int moraleLevel;
    private int roofLevel;
    private int palisadeLevel;
    private int weaponLevel;

    public Levels() {
        this.moraleLevel = 0;
        this.roofLevel = 0;
        this.palisadeLevel = 0;
        this.weaponLevel = 0;
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
}
