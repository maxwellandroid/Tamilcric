package com.maxwell.tamilcric.model;

import java.util.List;

public class ResultNewModel {
    String group;
    String groupId;
    List<ResultMatchesModels> resultMatchesModelsList;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<ResultMatchesModels> getResultMatchesModelsList() {
        return resultMatchesModelsList;
    }

    public void setResultMatchesModelsList(List<ResultMatchesModels> resultMatchesModelsList) {
        this.resultMatchesModelsList = resultMatchesModelsList;
    }
}
