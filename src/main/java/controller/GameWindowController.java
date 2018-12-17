package controller;

import engine.action.Action;
import model.cards.IdeaCard;
import model.character.MainCharacter;
import model.elements.Marker;
import model.elements.tiles.IslandTile;
import model.enums.elements.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameWindowController {

    public static Action chooseAction(List<Action> allActions) {
        List<Action> possibleActions = allActions.stream()
                .filter(Action::isPossibleToPerform)
                .collect(Collectors.toList());

        return possibleActions.get(0); // TODO: 2018-11-28
    }

    public static List<Marker> chooseMarkers(List<Action> allActions, Action action, List<Marker> allMarkers, boolean restriction) {
        List<Marker> possibleMarkers = allMarkers.stream()
                .filter(marker -> action.getAcceptedMarkerTypes().contains(marker.getMarkerType()))
                .collect(Collectors.toList());

        return new ArrayList<>(); // TODO: 2018-11-28
    }

    public static IslandTile chooseIslandTile(List<IslandTile> possibleIslandTiles) {
        IslandTile islandTile = null;
        // TODO: 2018-12-06
        return islandTile;
    }

    public static List<IdeaCard> chooseIdeaCards(List<IdeaCard> possibleIdeaCards, int value) {
        List<IdeaCard> chosenIdeas = new ArrayList<>();

        return chosenIdeas;
    }

    public static String chooseRoofOrPalisade() {

        // TODO: 2018-12-11
        return "ROOF";
    }

    public static List<MainCharacter> chooseStarvingCharacters(int value) {
        // TODO: 2018-12-11
        return new ArrayList<>();
    }

    public static Map<IslandTile, List<ResourceType>> chooseSourcesToCover(Map<IslandTile, List<ResourceType>> possibleSources, int value) {
        Map<IslandTile, List<ResourceType>> chosenSources = new HashMap<>();
        // TODO: 2018-12-13

        return chosenSources;
    }

    public static String chooseDeterminationOrMorale() {
        return "DETERMINATION";
    }
}
