package net.timelegacy.tlbuild.leveling;

import java.util.Date;
import java.util.UUID;
import org.bukkit.Location;

public class CustomPlayerReview {

  private UUID uuid;
  private int level;
  private Location location;
  private Date dateSubmitted;
  private Date lastSeen;

  public CustomPlayerReview(UUID uuid, int level, Location location, Date dateSubmitted, Date lastSeen) {
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

  public Date getDateSubmitted() {
    return dateSubmitted;
  }

  public Date getLastSeen() {
    return lastSeen;
  }
}
