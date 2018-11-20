package engine.handler;

import model.phase.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class PhaseCycleHandler {
    private static LinkedList<Phase> phases = new LinkedList<>(Arrays.asList(
            new EventPhase(),
            new MoralePhase(),
            new ProductionPhase(),
            new ActionPhase(),
            new NightPhase(),
            new WeatherPhase()
    ));

    private static Phase tmpPhase = null;

    public static LinkedList<Phase> getPhases() {
        return phases;
    }

    public static void setPhases(LinkedList<Phase> phases) {
        PhaseCycleHandler.phases = phases;
    }

    public static Phase nextPhase() {
        ListIterator<Phase> i = phases.listIterator();
        if (tmpPhase == null) {
            tmpPhase = phases.getFirst();
        } else {
            i.set(tmpPhase);
            tmpPhase = i.hasNext() ? i.next() : phases.getFirst();
        }
        return tmpPhase;
    }
}
