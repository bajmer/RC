package model.phase;

import model.data.GlobalData;

public interface Phase {
    void initializePhase(GlobalData globalData);

    void runPhase(GlobalData globalData);
}
