package model.character;

import model.elements.Marker;

import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    private int lives;
    private int maxLives;
    private int determination;
    private List<Marker> markers = new ArrayList<>();

    public Character(int lives) {
        this.lives = lives;
        this.maxLives = lives;
        this.determination = 0;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getMaxLives() {
        return maxLives;
    }

    public void setMaxLives(int maxLives) {
        this.maxLives = maxLives;
    }

    public int getDetermination() {
        return determination;
    }

    public void setDetermination(int determination) {
        this.determination = determination;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }
}
