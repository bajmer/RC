package engine;

import engine.handler.PhaseHandler;
import model.data.GlobalData;
import model.options.Options;
import model.phase.Phase;

public class GameEngine implements GameEngineListener {

	private GlobalData globalData;
	private boolean gameOver = false;

	public GameEngine(Options options) {
		GameInitializer gameInitializer = new GameInitializer(options);
		globalData = gameInitializer.initialize();
	}

	public void play() {
		int i = 0;
		while (i < 24 /*!gameOver*/) {
			Phase phase = PhaseHandler.nextPhase();
			phase.initializePhase(globalData);
			phase.runPhase(globalData);
			i++;
		}
	}

	@Override
	public void handleGameEnd() {
		gameOver = true;
	}

	@Override
	public void handleFridayDeath() {

	}
}
