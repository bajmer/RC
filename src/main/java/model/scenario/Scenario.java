package model.scenario;

public class Scenario {

    private int id;
    private int round;
    private int allRounds;

    public Scenario(int id, int allRounds) {
        this.id = id;
        this.allRounds = allRounds;
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
}
