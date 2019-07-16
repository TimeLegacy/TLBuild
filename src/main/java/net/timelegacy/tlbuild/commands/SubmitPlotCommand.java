package net.timelegacy.tlbuild.commands;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlbuild.managers.DataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubmitPlotCommand implements CommandExecutor {

  private TLBuild plugin;

  public SubmitPlotCommand(TLBuild plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      return true;
    }

    Player player = (Player) sender;

    DataManager dataManager = plugin.getDataManager();
    Plot plot = PlotPlayer.get(player.getName()).getCurrentPlot();

    // Check if the player already has a submission.
    if (dataManager.getPlayersNeedingReview().contains(player.getUniqueId())) {
      player.sendMessage("You have already submitted a review request. If your previous submission was a mistake use /unsubmitplot.");
      return true;
    }

    // Add to the queue.
    if (!plot.getOwners().contains(player.getUniqueId())) {
      player.sendMessage("You cannot submit a plot that isn't yours.");
      return true;
    }

    dataManager.getPlayersNeedingReview().add(player.getUniqueId());
    player.sendMessage("You have submitted your plot.");
    return true;
  }
}
