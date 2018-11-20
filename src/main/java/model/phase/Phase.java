package model.phase;

public interface Phase {
    default void initializePhase() {
        // TODO: 2018-11-13
    }

    void runPhase();

    default void endPhase() {
        // TODO: 2018-11-13
    }
}
