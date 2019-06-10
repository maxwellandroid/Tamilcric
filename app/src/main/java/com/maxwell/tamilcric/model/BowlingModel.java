package com.maxwell.tamilcric.model;

public class BowlingModel {
    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getOvers() {
        return overs;
    }

    public void setOvers(String overs) {
        this.overs = overs;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getWides() {
        return wides;
    }

    public void setWides(String wides) {
        this.wides = wides;
    }

    public String getNoBallas() {
        return noBallas;
    }

    public void setNoBallas(String noBallas) {
        this.noBallas = noBallas;
    }

    String player_name, overs,runs, wides, noBallas;
}
