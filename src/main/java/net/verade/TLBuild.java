package net.verade;

import net.verade.leveling.LevelPermissions;
import org.bukkit.plugin.java.JavaPlugin;

public class TLBuild extends JavaPlugin {

  private static TLBuild plugin = null;

  public static TLBuild getPlugin() {
    return plugin;
  }

  @Override
  public void onEnable() {
    plugin = this;
    getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
    AutoRestart.setup();

    LevelPermissions.setupLevels();
  }
}
