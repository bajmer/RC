package engine.handler;

import model.phase.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhaseHandler {
    private static List<Phase> phases = new ArrayList<>(Arrays.asList(
            new EventPhase(),
            new MoralePhase(),
            new ProductionPhase(),
            new ActionPhase(),
            new NightPhase(),
            new WeatherPhase()
    ));

    private static Phase tmpPhase = null;

    public static List<Phase> getPhases() {
        return phases;
    }

    public static void setPhases(List<Phase> phases) {
        PhaseHandler.phases = phases;
    }

    public static Phase nextPhase() {
        System.out.println(tmpPhase != null ? tmpPhase.getClass().getSimpleName() : "NULL");
        int imax = phases.size();
        for (int i = 0; i < imax; i++) {
            if (tmpPhase == null) {
                tmpPhase = phases.get(i);
                break;
            }
            if (phases.get(i) == tmpPhase) {
                if (i + 1 < imax) {
                    tmpPhase = phases.get(i + 1);
                    break;
                } else {
                    tmpPhase = phases.get(0);
                }
            }
        }
        return tmpPhase;
    }
}
