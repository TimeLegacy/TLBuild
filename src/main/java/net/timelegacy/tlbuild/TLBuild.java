package net.timelegacy.tlbuild;

import java.util.UUID;
import net.timelegacy.tlbuild.commands.ForumsCommand;
import net.timelegacy.tlbuild.commands.PlotAliasCommand;
import net.timelegacy.tlbuild.commands.ReviewCommand;
import net.timelegacy.tlbuild.commands.SubmitPlotCommand;
import net.timelegacy.tlbuild.commands.UnsubmitPlotCommand;
import net.timelegacy.tlbuild.events.PlayerJoinListener;
import net.timelegacy.tlbuild.guis.ReviewListGUI;
import net.timelegacy.tlbuild.leveling.LevelPermissions;
import net.timelegacy.tlbuild.managers.DataManager;
import net.timelegacy.tlbuild.managers.FileManager;
import net.timelegacy.tlbuild.managers.YAMLDataManager;
import net.timelegacy.tlcore.handler.ServerHandler;
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

    ServerHandler.getServers();

    for (UUID uuid : ServerHandler.getServers().keySet()) {
      System.out.println(uuid);
    }
  }

  @Override
  public void onDisable() {
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
  }

  private void registerEvents() {
    LevelPermissions.setupLevels();
    AutoRestart.setup();
    PlotActionBar.setup2();
    getServer().getPluginManager().registerEvents(new ReviewListGUI(this), this);
    getServer().getPluginManager().registerEvents(new ChatFormat(this), this);
    getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

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
