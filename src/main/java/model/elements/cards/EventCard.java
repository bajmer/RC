package model.elements.cards;

import model.enums.cards.event.EventEffectType;
import model.enums.cards.event.EventIconType;
import model.enums.cards.event.ThreatActionType;
import model.enums.cards.event.ThreatEffectType;

public class EventCard implements Card {
    private EventEffectType eventEffect;
    private EventIconType eventIcon;
    private ThreatActionType threatAction;
    private ThreatEffectType threatEffect;

    public EventCard(EventEffectType eventEffect, EventIconType eventIcon, ThreatActionType threatAction, ThreatEffectType threatEffect) {
        this.eventEffect = eventEffect;
        this.eventIcon = eventIcon;
        this.threatAction = threatAction;
        this.threatEffect = threatEffect;
    }

    public EventEffectType getEventEffect() {
        return eventEffect;
    }

    public void setEventEffect(EventEffectType eventEffect) {
        this.eventEffect = eventEffect;
    }

    public EventIconType getEventIcon() {
        return eventIcon;
    }

    public void setEventIcon(EventIconType eventIcon) {
        this.eventIcon = eventIcon;
    }

    public ThreatActionType getThreatAction() {
        return threatAction;
    }

    public void setThreatAction(ThreatActionType threatAction) {
        this.threatAction = threatAction;
    }

    public ThreatEffectType getThreatEffect() {
        return threatEffect;
    }

    public void setThreatEffect(ThreatEffectType threatEffect) {
        this.threatEffect = threatEffect;
    }
}
