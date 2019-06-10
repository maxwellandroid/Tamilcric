package com.maxwell.tamilcric.model;

import java.util.List;

public class MatchScheduleNewModel {

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public List<MatchScheduleModel> getMatchScheduleModelList() {
        return matchScheduleModelList;
    }

    public void setMatchScheduleModelList(List<MatchScheduleModel> matchScheduleModelList) {
        this.matchScheduleModelList = matchScheduleModelList;
    }

    String tournamentName;
    List<MatchScheduleModel> matchScheduleModelList;
}
