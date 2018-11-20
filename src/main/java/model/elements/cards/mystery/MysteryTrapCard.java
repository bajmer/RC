package model.elements.cards.mystery;

import model.enums.cards.mystery.MysteryTrapType;

public class MysteryTrapCard implements MysteryCard {
    private MysteryTrapType trapType;

    public MysteryTrapCard(MysteryTrapType trapType) {
        this.trapType = trapType;
    }

    public MysteryTrapType getTrapType() {
        return trapType;
    }

    public void setTrapType(MysteryTrapType trapType) {
        this.trapType = trapType;
    }
}
