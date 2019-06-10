package com.maxwell.tamilcric.model;

import java.util.List;

public class PointsTableNewModel {

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

    public List<PointsTableModel> getPointsTableModelList() {
        return pointsTableModelList;
    }

    public void setPointsTableModelList(List<PointsTableModel> pointsTableModelList) {
        this.pointsTableModelList = pointsTableModelList;
    }

    String group;
    String groupId;
    List<PointsTableModel> pointsTableModelList;
}
