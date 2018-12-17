package model.character;

import model.elements.Marker;
import model.enums.elements.MarkerType;

public class FridayCharacter extends Character {

    public FridayCharacter() {
        super(4);
        super.getMarkers().add(new Marker(MarkerType.valueOf("FRIDAY_MARKER")));
    }
}
