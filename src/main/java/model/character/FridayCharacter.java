package model.character;

import model.elements.Marker;
import model.enums.elements.MarkerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FridayCharacter implements Character {
    private Logger logger = LogManager.getLogger(FridayCharacter.class);

    private int lives;
    private int determination;
    private List<Marker> markers = new ArrayList<>();

    public FridayCharacter() {
        this.lives = 4;
        this.determination = 0;
        this.markers.add(new Marker(MarkerType.valueOf("FRIDAY_MARKER")));
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

    @Override
    public List<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }

    @Override
    public void changeLivesLevel(int value) {
        lives += value;
        if (lives > 4) {
            lives = 4;
        } else if (lives <= 0) {
            lives = 0;
        }
        logger.debug("The number of FRIDAY lives has been changed to: " + lives);
        if (lives <= 0) {
            // TODO: 2018-11-22 Handle Friday death
        }
    }

    @Override
    public void changeDeterminationLevel(int value) {
        determination += value;
        if (determination < 0) {
            changeLivesLevel(determination);
            determination = 0;
        }
        logger.debug("The number of FRIDAY determinations has been changed to: " + determination);
    }
}
