package model.options;

import java.util.List;

public class Options {

    private int scenarioNumber;
    private List<ChoosedCharacter> characters;
    private boolean activeFriday;
    private boolean activeDog;
    private int startingItemsNumber;

    public Options(int scenarioNumber, List<ChoosedCharacter> characters, boolean activeFriday, boolean activeDog, int startingItemsNumber) {
        this.scenarioNumber = scenarioNumber;
        this.characters = characters;
        this.activeFriday = activeFriday;
        this.activeDog = activeDog;
        this.startingItemsNumber = startingItemsNumber;
    }

    public int getScenarioNumber() {
        return scenarioNumber;
    }

    public List<ChoosedCharacter> getCharacters() {
        return characters;
    }

    public boolean isActiveFriday() {
        return activeFriday;
    }

    public boolean isActiveDog() {
        return activeDog;
    }

    public int getStartingItemsNumber() {
        return startingItemsNumber;
    }

}
