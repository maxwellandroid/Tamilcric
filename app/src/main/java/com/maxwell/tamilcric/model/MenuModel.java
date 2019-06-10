package com.maxwell.tamilcric.model;

public class MenuModel {

    public String menuName, position;
    public boolean hasChildren, isGroup;
  public int image;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, String position,int image) {

        this.menuName = menuName;
        this.position = position;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.image=image;
    }
}
