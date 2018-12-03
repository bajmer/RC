package engine.phase;

import model.cards.EventCard;
import model.character.MainCharacter;
import model.data.GlobalData;
import model.elements.tiles.IslandTile;
import model.enums.cards.event.IconType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventPhase implements Phase {
	private Logger logger = LogManager.getLogger(EventPhase.class);

	@Override
	public void initializePhase(GlobalData globalData) {
		globalData.getScenario().nextRound();
		int roundNumber = globalData.getScenario().getRound();
		logger.info("***********************************************************");
		logger.info("Round number: " + roundNumber);

		if (roundNumber == 1) {
			IslandTile beachTile = globalData.getDecks().getIslandTilesStack().removeFirst();
			globalData.getDiscoveredIslandTiles().add(beachTile);
			globalData.getAvailableTerrainTypes().add(beachTile.getTerrainType());
			globalData.getBoard().getTilePositionIdToIslandTile().put(1, beachTile);
			globalData.getBoard().getIslandTileToTilePosition().put(beachTile, 1);
            globalData.setCamp(beachTile);
            globalData.getGameParams().setFoodProduction(1);
            globalData.getGameParams().setWoodProduction(1);
		}

		int mainCharactersAmount = Math.toIntExact(globalData.getCharacters().stream().filter(character -> character instanceof MainCharacter).count());
		int firstPlayerId = (globalData.getScenario().getRound() - 1) % mainCharactersAmount;

		globalData.setFirstPlayer((MainCharacter) globalData.getCharacters().get(firstPlayerId));
		logger.debug("First player: " + globalData.getFirstPlayer().getProfession());

		logger.info("---> Phase: EVENT");
	}

	@Override
	public void runPhase(GlobalData globalData) {
		EventCard card = globalData.getDecks().getEventCardsDeck().removeFirst();
        logger.info("Event: " + card.getEvent());
        card.handleEvent();

        IconType icon = card.getIcon();
        logger.info("Icon: " + icon);
        handleIcon(icon, globalData);

		globalData.getThreatActionCards().addFirst(card);
		if (globalData.getThreatActionCards().size() > 2) {
			EventCard threatCard = globalData.getThreatActionCards().removeLast();
            logger.info("Threat: " + threatCard.getThreat());
            card.handleThreatEffect();
        }
    }

    private void handleIcon(IconType icon, GlobalData globalData) {
        if (icon == IconType.BOOK) {
            int scenarioId = globalData.getScenario().getId();
            if (scenarioId == 1) {
                logger.info("No effects.");
            } else if (scenarioId == 2) {

            } else if (scenarioId == 3) {

            } else if (scenarioId == 4) {

            } else if (scenarioId == 5) {

            } else if (scenarioId == 6) {

            }
        } else if (icon == IconType.BUILDING_ADVENTURE) {
            globalData.getDecks().setTokenOnBuildingAdventureDeck(true);
        } else if (icon == IconType.RESOURCES_ADVENTURE) {
            globalData.getDecks().setTokenOnResourcesAdventureDeck(true);
        } else if (icon == IconType.EXPLORATION_ADVENTURE) {
            globalData.getDecks().setTokenOnExplorationAdventureDeck(true);
		}
	}
}
