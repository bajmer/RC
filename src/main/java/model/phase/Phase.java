package model.phase;

import model.data.GlobalData;

public interface Phase {
    default void initializePhase() {
        // TODO: 2018-11-13
    }

    void runPhase(GlobalData globalData);

    default void endPhase() {
        // TODO: 2018-11-13
    }
}
