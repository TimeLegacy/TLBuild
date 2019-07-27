package net.timelegacy.tlbuild.commands;

import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlcore.datatype.Chat;
import net.timelegacy.tlcore.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLevelCommand implements CommandExecutor {

  private final TLBuild plugin;

  public SetLevelCommand(TLBuild plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Player Only!");
      return true;
    }

    Player player = (Player) sender;

    if (!player.hasPermission("tlbuild.setlevel")) {
      player.sendMessage("No Perms");
      return true;
    }

    int length = args.length;

    if (length == 0) {
      player.sendMessage(MessageUtils.colorize("&e&lLevel Commands"));
      player.sendMessage(MessageUtils.colorize("/setlevel <level>"));
      return true;
    }

    int level = 1;

    try {
      level = Integer.parseInt(args[0]);

      if (level > 3) {
        level = 3;
        player.sendMessage("It seems you tried to put yourself higher than is currently acceptable. I've gone ahead and just set you to the highest level instead.");
      }
    } catch (IllegalArgumentException e) {
      player.sendMessage("Please provide a number.");
    }

    plugin.getDataManager().setPlayerLevel(player.getUniqueId(), level);

    int currentPlayerLevel = plugin.getDataManager().getPlayerLevel(player.getUniqueId());
    String format = MessageUtils.colorize("&7[&6" + currentPlayerLevel + "&7] ");
    Chat.getPlayerChat(player).setPrefix(format);

    player.sendMessage("You set your level to " + level);
    return false;
  }
}
