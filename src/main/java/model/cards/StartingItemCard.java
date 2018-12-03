package model.cards;

import model.enums.cards.StartingItemType;

public class StartingItemCard {
    private static final int maxUseNumber = 2;
    private StartingItemType itemType;
    private int useLeftNumber;

    public StartingItemCard(StartingItemType itemType) {
        this.itemType = itemType;
        this.useLeftNumber = maxUseNumber;
    }

    public StartingItemType getItemType() {
        return itemType;
    }

    public void setItemType(StartingItemType itemType) {
        this.itemType = itemType;
    }

    public int getUseLeftNumber() {
        return useLeftNumber;
    }

    public void setUseLeftNumber(int useLeftNumber) {
        this.useLeftNumber = useLeftNumber;
    }
}
