package model.elements.cards.adventure;

import model.enums.cards.adventure.ExplorationAdventureType;
import model.enums.cards.event.EventEffectType;

public class ExplorationAdventureCard implements AdventureCard {

    private ExplorationAdventureType adventureType;
    private EventEffectType effectType;

    public ExplorationAdventureCard(ExplorationAdventureType adventureType, EventEffectType effectType) {
        this.adventureType = adventureType;
        this.effectType = effectType;
    }

    public ExplorationAdventureType getAdventureType() {
        return adventureType;
    }

    public void setAdventureType(ExplorationAdventureType adventureType) {
        this.adventureType = adventureType;
    }

    public EventEffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EventEffectType effectType) {
        this.effectType = effectType;
    }
}
