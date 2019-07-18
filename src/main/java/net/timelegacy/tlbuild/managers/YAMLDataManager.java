package net.timelegacy.tlbuild.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlcore.datatype.Chat;
import net.timelegacy.tlcore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class YAMLDataManager implements DataManager {

  private final TLBuild plugin;

  private Map<UUID, YAMLPlayerData> dataCache = new HashMap<>();

  private Map<UUID, UUID> playersUnderReview = new HashMap<>();
  private List<UUID> playersNeedingReview = new ArrayList<>();

  public YAMLDataManager(TLBuild plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean playerExists(UUID uuid) {
    return getPlayerData(uuid).playerFileExists();
  }

  @Override
  public void createPlayer(UUID uuid) {
    dataCache.computeIfAbsent(uuid, k -> new YAMLPlayerData(plugin, k));
  }

  @Override
  public void deletePlayer(UUID uuid) {
    dataCache.get(uuid).deletePlayerFile();
    dataCache.remove(uuid);
  }

  @Override
  public void loadDatabase() {
    File playerDataDirectory = new File(plugin.getFileManager().getPlayerDataDirectory());

    File[] files = playerDataDirectory.listFiles();

    if (files == null) {
      return;
    }

    for (File file : files) {
      if (file.isDirectory()) {
        continue;
      }

      if (!file.getName().endsWith(".yml")) {
        continue;
      }

      UUID uuid = UUID.fromString(file.getName().replace(".yml", ""));

      YAMLPlayerData playerData = new YAMLPlayerData(plugin, uuid);

      dataCache.put(uuid, playerData);
    }
  }

  @Override
  public void reloadDatabase() {
    dataCache.clear();
    loadDatabase();
  }

  @Override
  public void saveDatabase() {
    for (YAMLPlayerData playerData : dataCache.values()) {
      if (!playerData.hasBeenModified()) {
        continue;
      }

      playerData.saveDataFile();
    }
  }

  @Override
  public void purgeDatabase() {
    dataCache.clear();
    plugin.getFileManager().deleteDirectory(new File(plugin.getFileManager().getPlayerDataDirectory()));
    plugin.getFileManager().createDirectory(new File(plugin.getFileManager().getPlayerDataDirectory()));
  }

  @Override
  public void loadData(UUID uuid) {

  }

  @Override
  public void saveData(UUID uuid) {
    dataCache.get(uuid).saveDataFile();
    dataCache.remove(uuid);
  }

  @Override
  public void reloadData(UUID uuid) {

  }

  @Override
  public void purgeData(UUID uuid) {
    dataCache.get(uuid).deletePlayerFile();
  }

  @Override
  public void addPlayer(UUID uuid, Location location, Date dateSubmitted, Date lastSeen) {
  }

  @Override
  public void setPlayerLevel(UUID uuid, int level) {
    getPlayerConfig(uuid).set("level", level);
  }

  @Override
  public int getPlayerLevel(UUID uuid) {
    return getPlayerConfig(uuid).getInt("level");
  }

  @Override
  public void submitPlot(Player player, Location location) {
    getPlayerConfig(player.getUniqueId()).set("location.world", location.getWorld().getName());
    getPlayerConfig(player.getUniqueId()).set("location.x", location.getX());
    getPlayerConfig(player.getUniqueId()).set("location.y", location.getY());
    getPlayerConfig(player.getUniqueId()).set("location.z", location.getZ());
    getPlayerConfig(player.getUniqueId()).set("reviewStatus", "needsReview");

    playersNeedingReview.add(player.getUniqueId());

    getPlayerData(player.getUniqueId()).setHasBeenModified(true);
  }

  @Override
  public void updateStatus(UUID uuid, String status) {
    getPlayerConfig(uuid).set("reviewStatus", status);
  }

  @Override
  public void putPlayerUnderReview(UUID reviewer, UUID target) {
    getPlayerConfig(target).set("reviewStatus", "underReview");
    playersNeedingReview.remove(target);
    playersUnderReview.put(reviewer, target);
  }

  @Override
  public Location getPlayerSubmissionLocation(UUID uuid) {
    String world = getPlayerConfig(uuid).getString("location.world");
    int x = getPlayerConfig(uuid).getInt("location.x");
    int y = getPlayerConfig(uuid).getInt("location.y");
    int z = getPlayerConfig(uuid).getInt("location.z");

    return new Location(Bukkit.getWorld(world), x, y, z);
  }

  @Override
  public void acceptSubmission(UUID uuid) {
    UUID reviewer = uuid;
    UUID target = playersUnderReview.get(reviewer);

    System.out.println(getPlayerLevel(target));

    getPlayerConfig(target).set("reviewStatus", "none");
    setPlayerLevel(target, getPlayerLevel(target) + 1);

    System.out.println(getPlayerLevel(target));

    String format = MessageUtils.colorize("&7[&6" + getPlayerLevel(target) + "&7] ");
    Chat.getPlayerChat(Bukkit.getPlayer(target)).setPrefix(format);

    System.out.println(Bukkit.getOfflinePlayer(target).getName());

    playersUnderReview.remove(uuid);
  }

  @Override
  public void denySubmission(UUID uuid) {
    UUID reviewer = uuid;
    UUID target = playersUnderReview.get(reviewer);

    getPlayerConfig(target).set("reviewStatus", "none");

    playersUnderReview.remove(uuid);
  }

  @Override
  public void cancelReview(UUID uuid) {
    UUID reviewer = uuid;
    UUID target = playersUnderReview.get(reviewer);

    getPlayerConfig(target).set("reviewStatus", "needsReview");

    playersNeedingReview.add(target);
    playersUnderReview.remove(uuid);
  }

  @Override
  public String checkStatus(Location location) {
    return null;
  }

  @Override
  public Map<UUID, UUID> getPlayersUnderReview() {
    return playersUnderReview;
  }

  @Override
  public List<UUID> getPlayersNeedingReview() {
    return playersNeedingReview;
  }

  private YAMLPlayerData getPlayerData(UUID uuid) {
    return dataCache.computeIfAbsent(uuid, k -> new YAMLPlayerData(plugin, k));
  }

  private FileConfiguration getPlayerConfig(UUID uuid) {
    return dataCache.computeIfAbsent(uuid, k -> new YAMLPlayerData(plugin, k)).getPlayerConfig();
  }

}
