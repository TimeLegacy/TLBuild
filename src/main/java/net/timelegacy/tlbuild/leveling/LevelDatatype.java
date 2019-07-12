package net.timelegacy.tlbuild.leveling;

import java.util.List;

public class LevelDatatype {

  private int levelNum;
  private List<String> permissions;
  private String worldName;

  public LevelDatatype(int levelNum, String worldName, List<String> permissions) {
    this.levelNum = levelNum;
    this.permissions = permissions;
    this.worldName = worldName;
  }

  public List<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }

  public String getWorldName() {
    return worldName;
  }

  public void setWorldName(String worldName) {
    this.worldName = worldName;
  }

  public int getLevelNum() {
    return levelNum;
  }

  public void setLevelNum(int levelNum) {
    this.levelNum = levelNum;
  }
}
