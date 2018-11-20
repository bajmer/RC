package model.elements.cards;

import model.enums.ProfessionType;
import model.enums.cards.InventionType;
import model.requirements.Requirements;

public class InventionCard implements Card {
    private InventionType invention;
    private ProfessionType owner;
    private Requirements requirements;
    private boolean initialIdea;
    private boolean idea;
    private boolean multipleUse;

    public InventionCard(InventionType invention, ProfessionType owner, Requirements requirements, boolean initialIdea, boolean idea, boolean multipleUse) {
        this.invention = invention;
        this.owner = owner;
        this.requirements = requirements;
        this.initialIdea = initialIdea;
        this.idea = idea;
        this.multipleUse = multipleUse;
    }

    public InventionType getInvention() {
        return invention;
    }

    public void setInvention(InventionType invention) {
        this.invention = invention;
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

    public boolean isIdea() {
        return idea;
    }

    public void setIdea(boolean idea) {
        this.idea = idea;
    }

    public boolean isMultipleUse() {
        return multipleUse;
    }

    public void setMultipleUse(boolean multipleUse) {
        this.multipleUse = multipleUse;
    }
}
