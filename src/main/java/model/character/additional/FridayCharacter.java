package model.character.additional;

import model.character.Character;

public class FridayCharacter implements Character {
    private int lives;
    private int determination;

    public FridayCharacter() {
        this.lives = 4;
        this.determination = 0;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getDetermination() {
        return determination;
    }

    public void setDetermination(int determination) {
        this.determination = determination;
    }
}
