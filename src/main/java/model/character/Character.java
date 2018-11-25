package model.character;

import model.elements.Marker;

import java.util.List;

public interface Character {
    void changeLivesLevel(int value);

    void changeDeterminationLevel(int value);

    List<Marker> getMarkers();
}
