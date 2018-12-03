package model.cards;

import model.enums.cards.event.EventType;
import model.enums.cards.event.IconType;
import model.enums.cards.event.ThreatType;
import model.requirements.Requirements;

public class EventCard implements Card {
    private EventType event;
    private IconType icon;
    private ThreatType threat;

    public EventCard(EventType event, IconType icon, ThreatType threat) {
        this.event = event;
        this.icon = icon;
        this.threat = threat;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public IconType getIcon() {
        return icon;
    }

    public void setIcon(IconType icon) {
        this.icon = icon;
    }

    public ThreatType getThreat() {
        return threat;
    }

    public void setThreat(ThreatType threat) {
        this.threat = threat;
    }

    public void handleEvent() {
        switch (event) {
            case FOOD_CRATES:
                break;
            case WRECKED_LIFEBOAT:
                break;
            case CAPTAINS_CHEST:
                break;
            case WINTER_DEPRESSION:
                break;
            case CHRONIC_TIREDNESS:
                break;
            case HIGH_WATER:
                break;
            case FROST:
                break;
            case WRECKED_BALLOON:
                break;
            case PREDATORS_IN_THE_VICINITY:
                break;
            case NIGHT_HOWLING:
                break;
            case POISONING:
                break;
            case CULLED_AREA:
                break;
            case BODY_ON_THE_BEACH:
                break;
            case COLD_RAIN_1:
                break;
            case COLD_RAIN_2:
                break;
            case MEMORIES_OF_THE_CRUISE:
                break;
            case THUNDERSTORM:
                break;
            case PREDATORS:
                break;
            case CALL_OF_THE_WILD:
                break;
            case EARTHQUAKE:
                break;
            case DESTRUCTIVE_HURRICANE:
                break;
            case LANDSLIDE:
                break;
            case BAD_FEELINGS:
                break;
            case MESS_IN_THE_CAMP:
                break;
            case THE_ISLAND_REBELS_AGAINST_YOU:
                break;
            case NATURAL_DAM_BREAKS:
                break;
            case RAGING_RIVER:
                break;
            case FLOOD:
                break;
            case VERTIGO:
                break;
            case HEAVY_RAIN:
                break;
            case WEAKNESS:
                break;
            case SLOW_WORK:
                break;
            case RAIN:
                break;
            case PREDATORS_IN_THE_WOODS:
                break;
            case BAD_FATE:
                break;
            case BEAR:
                break;
            case CATACLYSM:
                break;
            case SEARCHING_FOR_A_NEW_PATH:
                break;
            case FIGHT: // (IF IT HAS THE ? SYMBOL)
                break;
            case ARGUMENT: // (IF IT HAS BOOK SYMBOL)
                break;
            case CLOUD_BURST:
                break;
            case HEAVY_CLOUDS:
                break;
            case RAVAGING_HURRICANE:
                break;
            case RAVISHING_WINDSTORM:
                break;
            case FIRE:
                break;
            case SLEEPLESS_NIGHT:
                break;
            case PREDATOR_IS_NEAR:
                break;
            case STORM_1:
                break;
            case STORM_2:
                break;
            case EXHAUSTING_NIGHT:
                break;
            case BROKEN_TREE:
                break;
            case STRONG_WIND:
                break;
            case WEATHER_BREAKDOWN:
                break;
            case MIST:
                break;
            case DEPRESSION:
                break;
            case DISASTER:
                break;
            case FLYING_SURPRISE:
                break;
            case DEVASTATING_BLOWS:
                break;
            case LACK_OF_HOPE:
                break;
            case LOSS_OF_HOPE:
                break;
            case FIGHT_IN_THE_DARK:
                break;
            case DANGEROUS_NIGHT:
                break;
            case NIGHT_ATTACK:
                break;
            case HOWLING_FROM_THE_WOODS:
                break;
            case SMOKE_ON_THE_HORIZON:
                break;
            case DROUGHT:
                break;
            case ROUGH_PASSAGE:
                break;
            case UNUSUALLY_COLD_NIGHT:
                break;
            case COUNCIL:
                break;
            case INSECTS:
                break;
            case HEAVY_RAIN_IS_COMING:
                break;
            case AWFUL_WEATHER:
                break;
            case PRECIPICE:
                break;
            case JAGUAR:
                break;
            case RAVENOUS_PREDATORS:
                break;
            case OTTERS:
                break;
            case ADV_EVENT_NAME:
                break;
        }
    }

    public void handleThreatAction() {

    }

    public void handleThreatEffect() {

    }

    public Requirements specifyRequirements() {
        Requirements requirements = new Requirements();

        return requirements;
    }
}
