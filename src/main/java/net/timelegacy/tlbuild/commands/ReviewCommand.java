package net.timelegacy.tlbuild.commands;

import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlbuild.guis.ReviewListGUI;
import net.timelegacy.tlbuild.managers.DataManager;
import net.timelegacy.tlcore.handler.RankHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviewCommand implements CommandExecutor {

  private final TLBuild plugin;

  public ReviewCommand(TLBuild plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Player only.");
      return true;
    }

    Player player = (Player) sender;
    int rankPriority = RankHandler.getRank(player.getUniqueId()).getPriority();
    if (!(rankPriority == 10 || rankPriority == 5)){// Builder or Founder
      player.sendMessage("no perms"); //TODO update this with a proper message from tac
      return true;
    }

    int length = args.length;

    if (length == 0) {
      player.sendMessage("/review help");
      player.sendMessage("/review list");
      player.sendMessage("/review comment <message>");
      player.sendMessage("/review cancel");
      player.sendMessage("/review <accept/deny>");
      return true;
    }

    if (length == 1) {
      if (args[0].equalsIgnoreCase("list")) {
        new ReviewListGUI(plugin).openGUI(player, 1);
        player.sendMessage("Here's a list of players who need their plots reviewed.");
        return true;
      }

      DataManager dataManager = plugin.getDataManager();

      if (args[0].equalsIgnoreCase("cancel")) {
        if (!dataManager.getPlayersUnderReview().containsKey(player.getUniqueId())) {
          player.sendMessage("You aren't reviewing anyone right now.");
          return true;
        }

        dataManager.cancelReview(player.getUniqueId());

        player.sendMessage("Review cancelled.");
        return true;
      }

      if (args[0].equalsIgnoreCase("accept")) {
        if (!dataManager.getPlayersUnderReview().containsKey(player.getUniqueId())) {
          player.sendMessage("You aren't reviewing anyone right now.");
          return true;
        }

        dataManager.acceptSubmission(player.getUniqueId());

        player.sendMessage("Accepted Review");
        return true;
      }

      if (args[0].equalsIgnoreCase("deny")) {
        if (!dataManager.getPlayersUnderReview().containsKey(player.getUniqueId())) {
          player.sendMessage("You aren't reviewing anyone right now.");
          return true;
        }

        dataManager.denySubmission(player.getUniqueId());

        player.sendMessage("Denied Review");
        return true;
      }

      return true;
    }

    if (length > 1) {
      StringBuilder alias = new StringBuilder();
      for (int i = 1; i < args.length; i++) {
        alias.append(args[i]).append(" ");
      }

      player.sendMessage(alias.toString());
    }

    return false;
  }

}
