package net.timelegacy.tlbuild.leveling;

import java.util.List;

public class LevelDatatype {

  private int levelNum;
  private List<String> permissions;

  public LevelDatatype(int levelNum, List<String> permissions) {
    this.levelNum = levelNum;
    this.permissions = permissions;
  }

  public List<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }

  public int getLevelNum() {
    return levelNum;
  }

  public void setLevelNum(int levelNum) {
    this.levelNum = levelNum;
  }
}