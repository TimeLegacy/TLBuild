package net.timelegacy.tlbuild;

import net.timelegacy.tlbuild.commands.ForumsCommand;
import net.timelegacy.tlbuild.commands.GetLevelCommand;
import net.timelegacy.tlbuild.commands.LevelPerksCommand;
import net.timelegacy.tlbuild.commands.PlotAliasCommand;
import net.timelegacy.tlbuild.commands.ReviewCommand;
import net.timelegacy.tlbuild.commands.SetLevelCommand;
import net.timelegacy.tlbuild.commands.SubmitPlotCommand;
import net.timelegacy.tlbuild.commands.UnsubmitPlotCommand;
import net.timelegacy.tlbuild.guis.ReviewListGUI;
import net.timelegacy.tlbuild.hooks.PlaceholderAPIHook;
import net.timelegacy.tlbuild.leveling.LevelPermissions;
import net.timelegacy.tlbuild.listeners.PlayerJoinListener;
import net.timelegacy.tlbuild.listeners.PlayerLeaveListener;
import net.timelegacy.tlbuild.listeners.PortalListener;
import net.timelegacy.tlbuild.managers.DataManager;
import net.timelegacy.tlbuild.managers.FileManager;
import net.timelegacy.tlbuild.managers.YAMLDataManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TLBuild extends JavaPlugin {

  private static TLBuild plugin = null;

  private DataManager dataManager;
  private FileManager fileManager;

  @Override
  public void onEnable() {
    plugin = this;

    registerClasses();
    registerCommands();
    registerEvents();

    fileManager.checkForAndCreateFiles();

    dataManager.loadDatabase();

    new PlaceholderAPIHook(this).register();
  }

  @Override
  public void onDisable() {
    dataManager.saveDatabase();
  }

  public static TLBuild getPlugin() {
    return plugin;
  }

  private void registerClasses() {
    this.fileManager = new FileManager(this);
    this.dataManager = new YAMLDataManager(this);
  }

  private void registerCommands() {
    getCommand("review").setExecutor(new ReviewCommand(this));
    getCommand("plotalias").setExecutor(new PlotAliasCommand());
    getCommand("submitplot").setExecutor(new SubmitPlotCommand(this));
    getCommand("unsubmitplot").setExecutor(new UnsubmitPlotCommand(this));
    getCommand("forums").setExecutor(new ForumsCommand());
    getCommand("setlevel").setExecutor(new SetLevelCommand(this));
    getCommand("getlevel").setExecutor(new GetLevelCommand(this));
    getCommand("levelperks").setExecutor(new LevelPerksCommand());
  }

  private void registerEvents() {
    LevelPermissions.setupLevels();
    AutoRestart.setup();
    PlotActionBar.setup2();
    PluginManager pm = getServer().getPluginManager();

    pm.registerEvents(new ReviewListGUI(this), this);
    pm.registerEvents(new ChatFormat(this), this);
    pm.registerEvents(new PlayerJoinListener(this), this);
    pm.registerEvents(new PortalListener(this), this);
    pm.registerEvents(new PlayerLeaveListener(), this);

    new BukkitRunnable() {

      @Override
      public void run() {
        dataManager.saveDatabase();
      }
    }.runTaskTimerAsynchronously(this, 0, 20 * 60 /* * 5*/);
  }

  public DataManager getDataManager() {
    return dataManager;
  }

  public FileManager getFileManager() {
    return fileManager;
  }
}
