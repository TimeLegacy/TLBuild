package net.timelegacy.tlbuild;

import net.timelegacy.tlbuild.commands.PlotAliasCommand;
import net.timelegacy.tlbuild.commands.ReviewCommand;
import net.timelegacy.tlbuild.commands.SubmitPlotCommand;
import net.timelegacy.tlbuild.commands.UnsubmitPlotCommand;
import net.timelegacy.tlbuild.guis.ReviewListGUI;
import net.timelegacy.tlbuild.managers.DataManager;
import net.timelegacy.tlbuild.managers.MongoDBDataManager;
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
    getCommand("plotalias").setExecutor(new PlotAliasCommand());
    getCommand("submitplot").setExecutor(new SubmitPlotCommand(this));
    getCommand("unsubmitplot").setExecutor(new UnsubmitPlotCommand(this));
  }

  private void registerEvents() {
    AutoRestart.setup();
    PlotActionBar.setup2();
    getServer().getPluginManager().registerEvents(new ReviewListGUI(this), this);
    getServer().getPluginManager().registerEvents(new ChatFormat(this), this);
  }

  public DataManager getDataManager() {
    return dataManager;
  }
}
