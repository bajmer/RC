package model.scenario;

public class Scenario {

    private int id;
    private int round;
    private int allRounds;
    private int totemCounter;

    public Scenario(int id, int allRounds) {
        this.id = id;
        this.allRounds = allRounds;
        this.round = 0;
        this.totemCounter = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getAllRounds() {
        return allRounds;
    }

    public void setAllRounds(int allRounds) {
        this.allRounds = allRounds;
    }

    public int getTotemCounter() {
        return totemCounter;
    }

    public void setTotemCounter(int totemCounter) {
        this.totemCounter = totemCounter;
    }

//    public void nextRound() {
//        round += 1;
//        if (round > allRounds) {
//            // TODO: 2018-11-21 The end
//        }
//    }
//
//    public void handleBookIcon(GlobalData globalData) {
//
//    }
}
