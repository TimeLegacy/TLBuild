package net.timelegacy.tlbuild;

import net.timelegacy.tlbuild.commands.PlotCommand;
import net.timelegacy.tlbuild.commands.ReviewCommand;
import net.timelegacy.tlbuild.guis.ReviewListGUI;
import net.timelegacy.tlbuild.leveling.LevelPermissions;
import net.timelegacy.tlbuild.leveling.events.PlayerEvents;
import net.timelegacy.tlbuild.managers.MongoDBDataManager;
import net.timelegacy.tlbuild.managers.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TLBuild extends JavaPlugin {

  private static TLBuild plugin = null;

  private DataManager dataManager;

  @Override
  public void onEnable() {
    plugin = this;

    registerClasses();
    registerCommands();
    registerEvents();

    //getCommand("plotalias").setExecutor(new CommandHandler());
  }

  @Override
  public void onDisable() {
  }

  public static TLBuild getPlugin() {
    return plugin;
  }

  private void registerClasses() {
    this.dataManager = new MongoDBDataManager();
  }

  private void registerCommands() {
    getCommand("review").setExecutor(new ReviewCommand(this));
    getCommand("plot").setExecutor(new PlotCommand(this));
  }

  private void registerEvents() {
    getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
    AutoRestart.setup();
    LevelPermissions.setupLevels();
    PlotActionBar.setup();
    getServer().getPluginManager().registerEvents(new ReviewListGUI(this), this);
    getServer().getPluginManager().registerEvents(new ChatFormat(this), this);
  }

  public DataManager getDataManager() {
    return dataManager;
  }
}
