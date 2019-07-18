package net.timelegacy.tlbuild.managers;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import net.timelegacy.tlbuild.TLBuild;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YAMLPlayerData {

  private final UUID uuid;
  private File playerFile;
  private FileConfiguration playerConfig;
  private boolean hasBeenModified = false;

  public YAMLPlayerData(TLBuild plugin, UUID uuid) {
    this.uuid = uuid;
    this.playerFile = new File(plugin.getFileManager().getPlayerDataDirectory(), uuid + ".yml");
    this.playerConfig = YamlConfiguration.loadConfiguration(playerFile);
    prerequisiteCheck();
  }

  private void prerequisiteCheck() {
    if (playerFileExists()) {
      return;
    }

    createPlayerFile(uuid);
    createDefaultSections();
  }

  private void createDefaultSections() {
    try {
      if (playerConfig.getConfigurationSection("uuid") == null) {
        playerConfig.createSection("uuid");
        playerConfig.set("uuid", uuid.toString());
        hasBeenModified = true;
      }

      if (playerConfig.getConfigurationSection("level") == null) {
        playerConfig.set("level", 1);
        hasBeenModified = true;
      }

      if (playerConfig.getConfigurationSection("location") == null) {
        playerConfig.createSection("location");
        hasBeenModified = true;
      }

      if (playerConfig.getConfigurationSection("location.world") == null) {
        playerConfig.set("location.world", "unknown");
        hasBeenModified = true;
      }

      if (playerConfig.getConfigurationSection("location.x") == null) {
        playerConfig.set("location.x", 0);
        hasBeenModified = true;
      }

      if (playerConfig.getConfigurationSection("location.y") == null) {
        playerConfig.set("location.y", 0);
        hasBeenModified = true;
      }

      if (playerConfig.getConfigurationSection("location.z") == null) {
        playerConfig.set("location.z", 0);
        hasBeenModified = true;
      }

      if (playerConfig.getConfigurationSection("dateSubmitted") == null) {
        playerConfig.set("dateSubmitted", 0L);
        hasBeenModified = true;
      }

      if (playerConfig.getConfigurationSection("lastSeen") == null) {
        playerConfig.set("lastSeen", 0L);
        hasBeenModified = true;
      }

      if (playerConfig.getConfigurationSection("reviewStatus") == null) {
        playerConfig.set("reviewStatus", "none");
        hasBeenModified = true;
      }

      playerConfig.save(playerFile);
    } catch (IOException e) {
      System.out.println("Could not generate player data file.");
      e.printStackTrace();
    }
  }

  public void saveDataFile() {
    prerequisiteCheck();

    try {
      playerConfig.save(playerFile);
      hasBeenModified = false;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean playerFileExists() {
    return playerFile.exists();
  }

  private void createPlayerFile(UUID uuid) {
    boolean fileCreated = playerFileExists();

    if (!fileCreated) {
      try {
        fileCreated = playerFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if (!fileCreated) {
      System.out.println("An error occured while creating " + Bukkit.getPlayer(uuid).getName() + "'s data file.");
    }
  }

  public boolean deletePlayerFile() {
    return playerFile.delete();
  }

  // playerFile getter and setter.
  public File getPlayerFile() {
    return playerFile;
  }

  public void setPlayerFile(File playerFile) {
    this.playerFile = playerFile;
  }

  // playerConfig getter and setter.
  public FileConfiguration getPlayerConfig() {
    return playerConfig;
  }

  public void setPlayerConfig(FileConfiguration playerConfig) {
    this.playerConfig = playerConfig;
  }

  // hasBeenModified getter and setter.
  public boolean hasBeenModified() {
    return hasBeenModified;
  }

  public void setHasBeenModified(boolean hasBeenModified) {
    this.hasBeenModified = hasBeenModified;
  }
}
