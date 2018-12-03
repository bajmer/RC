package engine.action;

import model.cards.BeastCard;
import model.character.Character;
import model.data.GlobalData;
import model.elements.Marker;
import model.enums.ActionType;
import model.enums.cards.BeastType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HuntingAction extends Action {
    private Logger logger = LogManager.getLogger(HuntingAction.class);

    public HuntingAction(ActionType actionType) {
        super(actionType);
    }

    @Override
    public void performTheAction(GlobalData globalData) {
        BeastCard beastCard = globalData.getAvailableBeastCards().removeFirst();
        BeastType beastType = beastCard.getBeast();
        int strength = beastCard.getStrength();
        int weaponDamage = beastCard.getWeaponLevelDecrease();
        int food = beastCard.getFoodAmount();
        int furs = beastCard.getFurAmount();
        StringBuilder sb = new StringBuilder();
        super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", " ")));

        logger.info("++ Action: HUNTING, Characters: " + sb.toString() + ", Beast: " + beastType.toString() + ", Strength: " + strength
                + ", Weapon damage: " + weaponDamage + ", Food: " + food + ", Furs: " + furs);

        Marker leaderMarker = super.getAssignedMarkers().get(0);
        Character leader = globalData.getCharacters().stream()
                .filter(character -> character.getMarkers().contains(leaderMarker))
                .findFirst().get();
        int weaponLevel = globalData.getGameParams().getWeaponLevel();
        if (weaponLevel < strength) {
            leader.changeLivesLevel(weaponLevel - strength);
        }
        globalData.changeWeaponLevel(weaponDamage, leader);
        globalData.getFutureResources().setFoodAmount(globalData.getFutureResources().getFoodAmount() + food);
        globalData.getFutureResources().setFurAmount(globalData.getFutureResources().getFurAmount() + furs);
    }
}
