package net.timelegacy.tlbuild.commands;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlbuild.managers.DataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotCommand implements CommandExecutor {

  private final TLBuild plugin;

  public PlotCommand(TLBuild plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be used by a player!");
      return true;
    }

    int length = args.length;
    Player player = (Player) sender;

    if (length == 0) {
      player.sendMessage("/plot submit");
      player.sendMessage("/plot unsubmit");
      return true;
    }

    if (length == 1) {
      DataManager dataManager = plugin.getDataManager();
      Plot plot = PlotPlayer.get(player.getName()).getCurrentPlot();

      if (args[0].equalsIgnoreCase("submit")) {
        // Check if the player already has a submission.
        if (dataManager.getPlayersNeedingReview().contains(player.getUniqueId())) {
          player.sendMessage("You have already submitted a review request. If your previous submission was a mistake use /plot unsubmit.");
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

      if (args[0].equalsIgnoreCase("unsubmit")) {
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

    return false;
  }
}
