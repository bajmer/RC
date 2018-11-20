package engine;

import model.data.GlobalData;
import model.options.Options;

public class GameEngine {

    private GlobalData globalData;

    public GameEngine(Options options) {
        GameInitializer gameInitializer = new GameInitializer(options);
        globalData = gameInitializer.initialize();

    }
}
