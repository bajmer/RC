package model.phase;

import engine.RequirementsValidator;
import engine.action.Action;
import model.data.GlobalData;
import model.elements.Marker;
import model.resources.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ActionPhase implements Phase {
    private Logger logger = LogManager.getLogger(ActionPhase.class);

    private List<Action> actions = new ArrayList<>();
    private List<Marker> availableMarkers = new ArrayList<>();
    private Resources actionResources = new Resources();

    @Override
    public void initializePhase(GlobalData globalData) {
        logger.info("---> Phase: ACTION");
        // TODO: 2018-11-23 Copy available resources
        actionResources.setFoodAmount(globalData.getAvailableResources().getFoodAmount());
        actionResources.setLongTermFoodAmount(globalData.getAvailableResources().getLongTermFoodAmount());
        actionResources.setWoodAmount(globalData.getAvailableResources().getWoodAmount());
        actionResources.setHideAmount(globalData.getAvailableResources().getHideAmount());

        // TODO: 2018-11-23 Prepare markers
        globalData.getCharacters().forEach(character -> availableMarkers.addAll(character.getMarkers()));
        availableMarkers.addAll(globalData.getAvailableActionHelpers());

        // TODO: 2018-11-23 Prepare all available actions
        List<Action> availableActions = new ArrayList<>();
        globalData.getThreatActionCards().forEach(threat -> {
            boolean availableAction = RequirementsValidator.validate(actionResources, threat.getThreatAction());

        });


        // TODO: 2018-11-23 Choose some actions

        // TODO: 2018-11-23 Sort actions order
    }

    @Override
    public void runPhase(GlobalData globalData) {

        // TODO: 2018-11-23 Handle actions
        actions.forEach(Action::performTheAction);

    }
}
