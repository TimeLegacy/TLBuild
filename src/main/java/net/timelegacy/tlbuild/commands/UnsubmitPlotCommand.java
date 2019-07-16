package net.timelegacy.tlbuild.commands;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlbuild.managers.DataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnsubmitPlotCommand implements CommandExecutor {

  private TLBuild plugin;

  public UnsubmitPlotCommand(TLBuild plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      return true;
    }

    Player player = (Player) sender;

    DataManager dataManager = plugin.getDataManager();

    // Check to see if they have submitted a plot in the first place.
    if (!dataManager.getPlayersNeedingReview().contains(player.getUniqueId())) {
      player.sendMessage("You have not submitted a plot!");
      return true;
    }

    // Remove from queue.
    dataManager.getPlayersNeedingReview().remove(player.getUniqueId());
    player.sendMessage("You have removed yourself from the review queue.");
    return true;
  }

}
