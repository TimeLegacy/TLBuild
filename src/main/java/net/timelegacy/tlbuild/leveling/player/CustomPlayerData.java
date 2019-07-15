package net.timelegacy.tlbuild.leveling.player;

import java.util.UUID;

public class CustomPlayerData {

  private UUID uuid;
  private String localizedName;
  private boolean isUnderReview = false;
  private int level;

  public CustomPlayerData(UUID uuid, String localizedName, int level) {
    this.uuid = uuid;
    this.localizedName = localizedName;
    this.level = level;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getLocalizedName() {
    return localizedName;
  }

  public void setLocalizedName(String localizedName) {
    this.localizedName = localizedName;
  }

  public boolean isUnderReview() {
    return isUnderReview;
  }

  public void setUnderReview(boolean underReview) {
    isUnderReview = underReview;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
