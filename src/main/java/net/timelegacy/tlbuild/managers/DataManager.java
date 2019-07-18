package net.timelegacy.tlbuild.managers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface DataManager {

  /**
   * Returns whether or not the player exists in the database.
   * */
  boolean playerExists(UUID uuid);

  /**
   * Create a database entry for the player.
   * */
  void createPlayer(UUID uuid);

  /**
   * Remove the database entry for the player.
   * */
  void deletePlayer(UUID uuid);


  void loadDatabase();

  void reloadDatabase();

  void saveDatabase();

  void purgeDatabase();

  void loadData(UUID uuid);

  void saveData(UUID uuid);

  void reloadData(UUID uuid);

  void purgeData(UUID uuid);

  void addPlayer(UUID uuid, Location location, Date dateSubmitted, Date lastSeen);

  void setPlayerLevel(UUID player, int level);

  int getPlayerLevel(UUID uuid);

  void submitPlot(Player player, Location location);

  void putPlayerUnderReview(UUID reviewer, UUID target);

  void updateStatus(UUID uuid, String status);

  Location getPlayerSubmissionLocation(UUID uuid);

  void acceptSubmission(UUID uuid);

  void denySubmission(UUID uuid);

  void cancelReview(UUID uuid);

  String checkStatus(Location location);

  Map<UUID, UUID> getPlayersUnderReview();

  List<UUID> getPlayersNeedingReview();

}
