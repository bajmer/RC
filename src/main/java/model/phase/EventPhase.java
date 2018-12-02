package model.phase;

import model.character.main.MainCharacter;
import model.data.GlobalData;
import model.elements.cards.EventCard;
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
			globalData.handleNewIslandTileDiscovery(beachTile);
			globalData.handleMovingShelterToNewTile(beachTile);
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
		logger.info("Event: " + card.getEventEffect());
		// TODO: 2018-11-21 Handle event

		IconType eventIcon = card.getEventIcon();
		logger.info("Event icon: " + eventIcon);
		// TODO: 2018-11-21 Handle event icon

		globalData.getThreatActionCards().addFirst(card);
		if (globalData.getThreatActionCards().size() > 2) {
			EventCard threatCard = globalData.getThreatActionCards().removeLast();
			logger.info("Threat effect: " + threatCard.getThreatAction());
			// TODO: 2018-11-21 Handle threat effect
		}
	}
}
