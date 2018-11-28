package engine;

import engine.action.Action;
import engine.action.BuildingAction;
import model.data.GlobalData;
import model.requirements.Requirements;
import model.resources.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequirementsValidator {

	private static Logger logger = LogManager.getLogger(RequirementsValidator.class);

	public static boolean checkAvailability(Action action, GlobalData globalData, Resources tmpResources) {
		Requirements requirements = action.getRequirements();
		Requirements gameState = new Requirements(
				globalData.getAvailableTerrainTypes(),
				globalData.getIdeas(),
				tmpResources,
				globalData.getGameParams(),
				globalData.isShelter());

		boolean ok = gameState.getRequiredTerrains().containsAll(requirements.getRequiredTerrains());
		ok &= gameState.getRequiredItems().containsAll(requirements.getRequiredItems());
		ok &= gameState.getRequiredResources().getFoodAmount() >= requirements.getRequiredResources().getFoodAmount();
		ok &= gameState.getRequiredResources().getLongTermFoodAmount() >= requirements.getRequiredResources().getLongTermFoodAmount();
		if (action instanceof BuildingAction) {
			BuildingAction buildingAction = (BuildingAction) action;
			if (buildingAction.getIdeaType() != null && buildingAction.getIdeaType().toString().startsWith("PARAM_")) {
				boolean woodOrHide = gameState.getRequiredResources().getWoodAmount() >= requirements.getRequiredResources().getWoodAmount();
				woodOrHide |= gameState.getRequiredResources().getHideAmount() >= requirements.getRequiredResources().getHideAmount();
				ok &= woodOrHide;
			} else {
				ok &= gameState.getRequiredResources().getWoodAmount() >= requirements.getRequiredResources().getWoodAmount();
				ok &= gameState.getRequiredResources().getHideAmount() >= requirements.getRequiredResources().getHideAmount();
			}
		} else {
			ok &= gameState.getRequiredResources().getWoodAmount() >= requirements.getRequiredResources().getWoodAmount();
			ok &= gameState.getRequiredResources().getHideAmount() >= requirements.getRequiredResources().getHideAmount();
		}
		ok &= !requirements.isRequiredShelter() || gameState.isRequiredShelter();
		ok &= gameState.getRequiredGameParams().getMoraleLevel() >= requirements.getRequiredGameParams().getMoraleLevel();
		ok &= gameState.getRequiredGameParams().getRoofLevel() >= requirements.getRequiredGameParams().getRoofLevel();
		ok &= gameState.getRequiredGameParams().getPalisadeLevel() >= requirements.getRequiredGameParams().getPalisadeLevel();
		ok &= gameState.getRequiredGameParams().getWeaponLevel() >= requirements.getRequiredGameParams().getWeaponLevel();

		logger.debug("Action " + action.getActionType().toString() + " availability: " + ok);
		return ok;
	}
}
