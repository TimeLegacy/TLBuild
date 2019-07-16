package net.timelegacy.tlbuild.leveling;

import java.util.UUID;
import org.bukkit.Location;

public class CustomPlayerReview {

  private UUID uuid;
  private int level;
  private Location location;
  private long dateSubmitted;
  private long lastSeen;

  public CustomPlayerReview(UUID uuid, int level, Location location, long dateSubmitted, long lastSeen) {
    this.uuid = uuid;
    this.level = level;
    this.location = location;
    this.dateSubmitted = dateSubmitted;
    this.lastSeen = lastSeen;
  }

  public UUID getUuid() {
    return uuid;
  }

  public int getLevel() {
    return level;
  }

  public Location getLocation() {
    return location;
  }

  public long getDateSubmitted() {
    return dateSubmitted;
  }

  public long getLastSeen() {
    return lastSeen;
  }
}
