package model.cards;

import model.enums.ProfessionType;
import model.enums.cards.IdeaType;
import model.requirements.Requirements;

public class IdeaCard {
    private IdeaType idea;
    private ProfessionType owner;
    private boolean initialIdea;
    private boolean transformedToInvention;
    private boolean multipleUse;

    public IdeaCard(IdeaType idea, ProfessionType owner, boolean initialIdea, boolean transformedToInvention, boolean multipleUse) {
        this.idea = idea;
        this.owner = owner;
        this.initialIdea = initialIdea;
        this.transformedToInvention = transformedToInvention;
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

    public boolean isTransformedToInvention() {
        return transformedToInvention;
    }

    public void setTransformedToInvention(boolean transformedToInvention) {
        this.transformedToInvention = transformedToInvention;
    }

    public boolean isMultipleUse() {
        return multipleUse;
    }

    public void setMultipleUse(boolean multipleUse) {
        this.multipleUse = multipleUse;
    }

    public Requirements specifyRequirements() {
        Requirements requirements = new Requirements();

        return requirements;
    }

    public void transformIdeaToInvention() {

    }
}
