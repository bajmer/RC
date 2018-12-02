package model.elements.cards;

import model.enums.cards.AdventureType;
import model.enums.cards.event.EventType;

public class AdventureCard {
    private AdventureType adventure;
    private EventType event;

    public AdventureCard(AdventureType adventure, EventType event) {
        this.adventure = adventure;
        this.event = event;
    }

    public AdventureType getAdventure() {
        return adventure;
    }

    public void setAdventure(AdventureType adventure) {
        this.adventure = adventure;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public void handleAdventure() {
        // TODO: 2018-11-30
    }

    public void handleEvent() {
        // TODO: 2018-11-30
    }
}
