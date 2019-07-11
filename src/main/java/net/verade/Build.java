package net.verade;

import net.verade.leveling.LevelPermissions;
import org.bukkit.plugin.java.JavaPlugin;

public class Build extends JavaPlugin {

  private static Build plugin = null;

  public static Build getPlugin() {
    return plugin;
  }

  @Override
  public void onEnable() {
    plugin = this;
    getCommand("website").setExecutor(new CommandHandler());
    getCommand("discord").setExecutor(new CommandHandler());
    getCommand("pl").setExecutor(new CommandHandler());
    getServer().getPluginManager().registerEvents(new EventHandler(), plugin);
    AutoRestart.setup();

    LevelPermissions.setupLevels();
  }
}
