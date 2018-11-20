package model.elements.cards.mystery;

import model.enums.cards.mystery.MysteryTreasureType;

public class MysteryTreasureCard implements MysteryCard {
    private MysteryTreasureType treasureType;

    public MysteryTreasureCard(MysteryTreasureType treasureType) {
        this.treasureType = treasureType;
    }

    public MysteryTreasureType getTreasureType() {
        return treasureType;
    }

    public void setTreasureType(MysteryTreasureType treasureType) {
        this.treasureType = treasureType;
    }
}
