package model.elements.cards;

import model.enums.cards.event.EventType;
import model.enums.cards.event.IconType;
import model.enums.cards.event.ThreatType;

public class EventCard implements Card {
    private EventType eventEffect;
    private IconType eventIcon;
    private ThreatType threatAction;

    public EventCard(EventType eventEffect, IconType eventIcon, ThreatType threatAction) {
        this.eventEffect = eventEffect;
        this.eventIcon = eventIcon;
        this.threatAction = threatAction;
    }

    public EventType getEventEffect() {
        return eventEffect;
    }

    public void setEventEffect(EventType eventEffect) {
        this.eventEffect = eventEffect;
    }

    public IconType getEventIcon() {
        return eventIcon;
    }

    public void setEventIcon(IconType eventIcon) {
        this.eventIcon = eventIcon;
    }

    public ThreatType getThreatAction() {
        return threatAction;
    }

    public void setThreatAction(ThreatType threatAction) {
        this.threatAction = threatAction;
    }

    public void handleEvent() {

    }

    public void handleIcon() {

    }

    public void handleThreat() {

    }
}
