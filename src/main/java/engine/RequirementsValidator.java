package engine;

import engine.action.Action;
import engine.action.BuildingAction;
import model.cards.IdeaCard;
import model.data.GlobalData;
import model.requirements.Requirements;
import model.resources.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

public class RequirementsValidator {

	private static Logger logger = LogManager.getLogger(RequirementsValidator.class);

    public static boolean checkAvailability(Action action, Resources tmpResources, boolean oneMoreWood) {
        int additionalWood = oneMoreWood ? 1 : 0;
		Requirements requirements = action.getRequirements();
		Requirements gameState = new Requirements(
                GlobalData.getAvailableTerrainTypes(),
                GlobalData.getIdeas().stream().map(IdeaCard::getIdea).collect(Collectors.toList()),
				tmpResources,
                GlobalData.getLevels(),
                GlobalData.isShelter());

		boolean ok = gameState.getRequiredTerrains().containsAll(requirements.getRequiredTerrains());
        ok &= gameState.getRequiredInventions().containsAll(requirements.getRequiredInventions());
		ok &= gameState.getRequiredResources().getFoodAmount() >= requirements.getRequiredResources().getFoodAmount();
		ok &= gameState.getRequiredResources().getLongTermFoodAmount() >= requirements.getRequiredResources().getLongTermFoodAmount();
		if (action instanceof BuildingAction) {
			BuildingAction buildingAction = (BuildingAction) action;
            if (buildingAction.getIdeaType().toString().startsWith("PARAM_")) {
                boolean woodOrFur = gameState.getRequiredResources().getWoodAmount() >= requirements.getRequiredResources().getWoodAmount() + additionalWood;
                woodOrFur |= gameState.getRequiredResources().getFurAmount() >= requirements.getRequiredResources().getFurAmount();
                ok &= woodOrFur;
			} else {
                ok &= gameState.getRequiredResources().getWoodAmount() >= requirements.getRequiredResources().getWoodAmount() + additionalWood;
                ok &= gameState.getRequiredResources().getFurAmount() >= requirements.getRequiredResources().getFurAmount();
			}
		} else {
			ok &= gameState.getRequiredResources().getWoodAmount() >= requirements.getRequiredResources().getWoodAmount();
            ok &= gameState.getRequiredResources().getFurAmount() >= requirements.getRequiredResources().getFurAmount();
		}
		ok &= !requirements.isRequiredShelter() || gameState.isRequiredShelter();
        ok &= gameState.getRequiredLevels().getMoraleLevel() >= requirements.getRequiredLevels().getMoraleLevel();
        ok &= gameState.getRequiredLevels().getRoofLevel() >= requirements.getRequiredLevels().getRoofLevel();
        ok &= gameState.getRequiredLevels().getPalisadeLevel() >= requirements.getRequiredLevels().getPalisadeLevel();
        ok &= gameState.getRequiredLevels().getWeaponLevel() >= requirements.getRequiredLevels().getWeaponLevel();

		logger.debug("Action " + action.getActionType().toString() + " availability: " + ok);
		return ok;
	}
}
