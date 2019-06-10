package com.maxwell.tamilcric.model;

public class BattingsModel {
    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getSixes() {
        return sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = sixes;
    }

    String player_name;
    String balls;
    String runs;
    String fours;
    String sixes;

    public String getStraikRate() {
        return straikRate;
    }

    public void setStraikRate(String straikRate) {
        this.straikRate = straikRate;
    }

    String straikRate;
}
