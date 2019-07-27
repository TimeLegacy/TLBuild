package net.timelegacy.tlbuild.commands;

import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlbuild.guis.ReviewListGUI;
import net.timelegacy.tlbuild.managers.DataManager;
import net.timelegacy.tlcore.utils.MessageUtils;
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
//    int rankPriority = RankHandler.getRank(player.getUniqueId()).getPriority();
//    if (!(rankPriority == 10 || rankPriority == 5)){// Builder or Founder
//      player.sendMessage("no perms"); //TODO update this with a proper message from tac
//      return true;
//    }

    if (!player.hasPermission("tlbuild.review")) {
      player.sendMessage(MessageUtils.colorize("&cYou do not have permission to use this command!"));
      return true;
    }

    int length = args.length;

    if (length == 0) {
      sendHelpMenu(player);
      return true;
    }

    if (length == 1) {
      if (args[0].equalsIgnoreCase("list")) {
        new ReviewListGUI(plugin).openGUI(player, 1);
        player.sendMessage(MessageUtils.colorize("&7Here's a list of players who need their plots reviewed."));
        return true;
      }

      DataManager dataManager = plugin.getDataManager();

      if (args[0].equalsIgnoreCase("cancel")) {
        if (!dataManager.getPlayersUnderReview().containsKey(player.getUniqueId())) {
          player.sendMessage(MessageUtils.colorize("&eYou aren't reviewing anyone right now."));
          return true;
        }

        dataManager.cancelReview(player.getUniqueId());

        player.sendMessage(MessageUtils.colorize("&eReview cancelled."));
        return true;
      }

      if (args[0].equalsIgnoreCase("accept")) {
        if (!dataManager.getPlayersUnderReview().containsKey(player.getUniqueId())) {
          player.sendMessage(MessageUtils.colorize("&eYou aren't reviewing anyone right now."));
          return true;
        }

        dataManager.acceptSubmission(player.getUniqueId());

        player.sendMessage(MessageUtils.colorize("&aAccepted Review"));
        return true;
      }

      if (args[0].equalsIgnoreCase("deny")) {
        if (!dataManager.getPlayersUnderReview().containsKey(player.getUniqueId())) {
          player.sendMessage(MessageUtils.colorize("&eYou aren't reviewing anyone right now."));
          return true;
        }

        dataManager.denySubmission(player.getUniqueId());

        player.sendMessage(MessageUtils.colorize("&cDenied Review"));
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

  private void sendHelpMenu(Player player) {
    player.sendMessage(MessageUtils.colorize("&e&lReview Commands"));
    player.sendMessage(MessageUtils.colorize("/review help"));
    player.sendMessage(MessageUtils.colorize("&7&o Shows this page."));
    player.sendMessage(MessageUtils.colorize("/review list"));
    player.sendMessage(MessageUtils.colorize("&7&o Opens a GUI with players needing review."));
    player.sendMessage(MessageUtils.colorize("/review comment <message>"));
    player.sendMessage(MessageUtils.colorize("&7&o COMING SOON"));
    player.sendMessage(MessageUtils.colorize("/review cancel"));
    player.sendMessage(MessageUtils.colorize("&7&o Cancel your review."));
    player.sendMessage(MessageUtils.colorize("/review <accept/deny>"));
    player.sendMessage(MessageUtils.colorize("&7&o Accept/Deny a submission."));
  }

}
