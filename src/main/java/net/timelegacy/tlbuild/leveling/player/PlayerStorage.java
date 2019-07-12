package net.timelegacy.tlbuild.leveling.player;

import java.io.File;
import java.io.IOException;
import net.timelegacy.tlbuild.TLBuild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerStorage {

  public static void createPlayer(Player player) {
    File playerFile = new File(
        TLBuild.getPlugin().getDataFolder().getPath() + "/players/" + player.getUniqueId().toString() + ".yml");

    try {
      playerFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

    playerConfig.set("uuid", player.getUniqueId().toString());

    //TODO store the rest of the shit that we need to store,
    // like their plots, locations, plots to be reviewed,
    // accepted plots, etc. @piajesse
  }
}
