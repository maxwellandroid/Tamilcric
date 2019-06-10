package com.maxwell.tamilcric.model;

public class MatchScheduleModel {

    public String getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    String matchNumber;
    String date;
    String time;
    String group;
    String ground;
    String tournamentName;
    String teamAName;
    String teamBName;
    String teamALogo;
    String teamBLogo;

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    String stadium;

    public String getTeamALogo() {
        return teamALogo;
    }

    public void setTeamALogo(String teamALogo) {
        this.teamALogo = teamALogo;
    }

    public String getTeamBLogo() {
        return teamBLogo;
    }

    public void setTeamBLogo(String teamBLogo) {
        this.teamBLogo = teamBLogo;
    }
}
