package engine.phase;

import engine.MethodRepository;
import model.cards.AdventureCard;
import model.cards.Card;
import model.cards.EventCard;
import model.character.MainCharacter;
import model.data.GlobalData;
import model.enums.ActionType;
import model.enums.cards.event.IconType;
import model.enums.elements.TokenType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventPhase implements Phase {
	private Logger logger = LogManager.getLogger(EventPhase.class);

	@Override
    public void initializePhase() {
        MethodRepository.nextRound();
        int roundNumber = GlobalData.getScenario().getRound();
		logger.info("***********************************************************");
		logger.info("Round number: " + roundNumber);

		if (roundNumber == 1) {
            MethodRepository.handleDiscoveryOfNewIslandTile(1);
            GlobalData.setCamp(GlobalData.getBoard().getTilePositionIdToIslandTile().get(1));
		}

        MethodRepository.nextFirstPlayer();

        GlobalData.getCharacters().stream()
                .filter(character -> character instanceof MainCharacter)
                .map(character -> (MainCharacter) character)
                .forEach(mainCharacter -> mainCharacter.setUseOfSpecialSkills(true));

		logger.info("---> Phase: EVENT");
	}

	@Override
    public void runPhase() {
        boolean waitForEvent = true;
        while (waitForEvent) {
            Card card = GlobalData.getDecks().getEventCardsDeck().removeFirst();
            if (card instanceof AdventureCard) {
                AdventureCard adventureCard = (AdventureCard) card;
                logger.info("Event: " + adventureCard.getEvent());
                adventureCard.handleEvent();
            } else {
                EventCard eventCard = (EventCard) card;
                logger.info("Event: " + eventCard.getEvent());
                eventCard.handleEvent();

                IconType icon = eventCard.getIcon();
                logger.info("Icon: " + icon);
                handleIcon(icon);

                GlobalData.getThreatActionCards().addFirst(eventCard);
                if (GlobalData.getThreatActionCards().size() > 2) {
                    EventCard threatCard = GlobalData.getThreatActionCards().removeLast();
                    logger.info("Threat: " + threatCard.getThreat());
                    eventCard.handleThreatEvent();
                }
                waitForEvent = false;
            }
        }
    }

    private void handleIcon(IconType icon) {
        if (icon == IconType.BOOK) {
            int scenarioId = model.data.GlobalData.getScenario().getId();
            switch (scenarioId) {
                case 1:
                    logger.info("Icon effect: No effect");
                    break;
                case 2:
                    logger.info("Icon effect: Mysterious fog");
                    break;
                case 3:
                    logger.info("Icon effect: Storm");
                    break;
                case 4:
                    logger.info("Icon effect: Dust eruption");
                    break;
                case 5:
                    logger.info("Icon effect: Cannibal attack");
                    break;
                case 6:
                    logger.info("Icon effect: Poor yield");
                    break;
            }
        } else if (icon == IconType.BUILDING_ADVENTURE) {
            MethodRepository.putTokenOnActionField(ActionType.BUILDING_ACTION, TokenType.ADVENTURE_TOKEN);
        } else if (icon == IconType.RESOURCES_ADVENTURE) {
            MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ADVENTURE_TOKEN);
        } else if (icon == IconType.EXPLORATION_ADVENTURE) {
            MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ADVENTURE_TOKEN);
		}
	}
}
