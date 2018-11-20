package model.elements.decks.cards.adventure;

import model.enums.cards.adventure.BuildingAdventureType;
import model.enums.cards.event.EventEffectType;

public class BuildingAdventureCard implements AdventureCard {

    private BuildingAdventureType adventureType;
    private EventEffectType effectType;

    public BuildingAdventureCard(BuildingAdventureType adventureType, EventEffectType effectType) {
        this.adventureType = adventureType;
        this.effectType = effectType;
    }

    public BuildingAdventureType getAdventureType() {
        return adventureType;
    }

    public void setAdventureType(BuildingAdventureType adventureType) {
        this.adventureType = adventureType;
    }

    public EventEffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EventEffectType effectType) {
        this.effectType = effectType;
    }
}
