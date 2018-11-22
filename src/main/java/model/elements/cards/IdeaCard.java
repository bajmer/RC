package model.elements.cards;

import model.enums.ProfessionType;
import model.enums.cards.IdeaType;
import model.requirements.Requirements;

public class IdeaCard implements Card {
    private IdeaType idea;
    private ProfessionType owner;
    private Requirements requirements;
    private boolean initialIdea;
    private boolean transformedToItem;
    private boolean multipleUse;

    public IdeaCard(IdeaType idea, ProfessionType owner, Requirements requirements, boolean initialIdea, boolean transformedToItem, boolean multipleUse) {
        this.idea = idea;
        this.owner = owner;
        this.requirements = requirements;
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

    public Requirements getRequirements() {
        return requirements;
    }

    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
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
