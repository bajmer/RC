package engine.action;

import engine.MethodRepository;
import model.cards.BeastCard;
import model.character.Character;
import model.data.GlobalData;
import model.enums.ActionType;
import model.enums.cards.BeastType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HuntingAction extends Action {
    private static boolean beastStrengthPlusOne;
    private Logger logger = LogManager.getLogger(HuntingAction.class);

    public HuntingAction(ActionType actionType) {
        super(actionType);
    }

    public static boolean isBeastStrengthPlusOne() {
        return beastStrengthPlusOne;
    }

    public static void setBeastStrengthPlusOne(boolean beastStrengthPlusOne) {
        HuntingAction.beastStrengthPlusOne = beastStrengthPlusOne;
    }

    @Override
    public void performTheAction(Character leader) {
        BeastCard beastCard = GlobalData.getAvailableBeastCards().removeFirst();
        BeastType beastType = beastCard.getBeast();
        int strength = beastCard.getStrength();
        if (beastStrengthPlusOne) {
            strength += 1;
            beastStrengthPlusOne = false;
        }
        int weaponDamage = beastCard.getWeaponLevelDecrease();
        int food = beastCard.getFoodAmount();
        int furs = beastCard.getFurAmount();
        StringBuilder sb = new StringBuilder();
        super.getAssignedMarkers().forEach(marker -> sb.append(marker.toString().replaceAll("_MARKER", " ")));

        logger.info("++ Action: HUNTING, Characters: " + sb.toString() + ", Beast: " + beastType.toString() + ", Strength: " + strength
                + ", Weapon damage: " + weaponDamage + ", Food: " + food + ", Furs: " + furs);

        int weaponLevel = GlobalData.getLevels().getWeaponLevel();
        if (weaponLevel < strength) {
            MethodRepository.changeLivesLevel(weaponLevel - strength, leader);
        }
        MethodRepository.changeWeaponLevel(weaponDamage, leader);
        GlobalData.getFutureResources().setFoodAmount(GlobalData.getFutureResources().getFoodAmount() + food);
        GlobalData.getFutureResources().setFurAmount(GlobalData.getFutureResources().getFurAmount() + furs);
    }
}
