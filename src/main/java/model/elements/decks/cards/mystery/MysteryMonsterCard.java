package model.elements.decks.cards.mystery;

import model.enums.cards.mystery.MysteryMonsterType;

public class MysteryMonsterCard implements MysteryCard {
    private MysteryMonsterType monsterType;

    public MysteryMonsterCard(MysteryMonsterType monsterType) {
        this.monsterType = monsterType;
    }

    public MysteryMonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MysteryMonsterType monsterType) {
        this.monsterType = monsterType;
    }
}
