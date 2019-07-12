package net.verade;

import net.verade.leveling.LevelPermissions;
import net.verade.leveling.events.PlayerEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class TLBuild extends JavaPlugin {

  private static TLBuild plugin = null;

  public static TLBuild getPlugin() {
    return plugin;
  }

  @Override
  public void onEnable() {
    plugin = this;
    getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
    AutoRestart.setup();

    LevelPermissions.setupLevels();
  }
}
