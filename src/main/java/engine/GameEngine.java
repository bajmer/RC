package engine;

import model.data.GlobalData;
import model.options.Options;

public class GameEngine implements GameEngineListener {

	private GlobalData globalData;
	private boolean gameOver = false;

	public GameEngine(Options options) {
		GameInitializer gameInitializer = new GameInitializer(options);
		globalData = gameInitializer.initialize();

	}

	public void play() {
//		while (!gameOver) {
//			Phase phase = PhaseCycleHandler.nextPhase();
//			phase.runPhase(globalData);
//		}
	}

	@Override
	public void handleGameEnd() {
		gameOver = true;
	}

	@Override
	public void handleFridayDeath() {

	}
}
