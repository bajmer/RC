package engine;

import engine.phase.Phase;
import engine.phase.PhaseHandler;
import model.options.Options;

public class GameEngine implements GameEngineListener {

	private boolean gameOver = false;

	public GameEngine(Options options) {
		GameInitializer gameInitializer = new GameInitializer(options);
        gameInitializer.initialize();
	}

	public void play() {
		int i = 0;
        while (i < 48 /*!gameOver*/) {
			Phase phase = PhaseHandler.nextPhase();
            phase.initializePhase();
            phase.runPhase();
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
