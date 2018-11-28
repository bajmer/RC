package model.elements.cards;

import model.enums.ProfessionType;
import model.enums.cards.IdeaType;

public class IdeaCard implements Card {
    private IdeaType idea;
    private ProfessionType owner;
    private boolean initialIdea;
    private boolean transformedToItem;
    private boolean multipleUse;

    public IdeaCard(IdeaType idea, ProfessionType owner, boolean initialIdea, boolean transformedToItem, boolean multipleUse) {
        this.idea = idea;
        this.owner = owner;
        this.initialIdea = initialIdea;
        this.transformedToItem = transformedToItem;
        this.multipleUse = multipleUse;
    }

    public IdeaType getIdea() {
        return idea;
    }

    public void setIdea(IdeaType idea) {
        this.idea = idea;
    }

    public ProfessionType getOwner() {
        return owner;
    }

    public void setOwner(ProfessionType owner) {
        this.owner = owner;
    }

    public boolean isInitialIdea() {
        return initialIdea;
    }

    public void setInitialIdea(boolean initialIdea) {
        this.initialIdea = initialIdea;
    }

    public boolean isTransformedToItem() {
        return transformedToItem;
    }

    public void setTransformedToItem(boolean transformedToItem) {
        this.transformedToItem = transformedToItem;
    }

    public boolean isMultipleUse() {
        return multipleUse;
    }

    public void setMultipleUse(boolean multipleUse) {
        this.multipleUse = multipleUse;
    }
}
