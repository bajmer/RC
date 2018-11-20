package model.elements.cards.adventure;

import model.enums.cards.adventure.ResourcesAdventureType;
import model.enums.cards.event.EventEffectType;

public class ResourcesAdventureCard implements AdventureCard {

    private ResourcesAdventureType adventureType;
    private EventEffectType effectType;

    public ResourcesAdventureCard(ResourcesAdventureType adventureType, EventEffectType effectType) {
        this.adventureType = adventureType;
        this.effectType = effectType;
    }

    public ResourcesAdventureType getAdventureType() {
        return adventureType;
    }

    public void setAdventureType(ResourcesAdventureType adventureType) {
        this.adventureType = adventureType;
    }

    public EventEffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EventEffectType effectType) {
        this.effectType = effectType;
    }
}
