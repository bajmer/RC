package model.elements.cards;

import model.enums.cards.MysteryType;

public class MysteryCard {

    private MysteryType mystery;

    public MysteryCard(MysteryType mystery) {
        this.mystery = mystery;
    }

    public MysteryType getMystery() {
        return mystery;
    }

    public void setMystery(MysteryType mystery) {
        this.mystery = mystery;
    }

    public void handleMystery() {

    }
}
