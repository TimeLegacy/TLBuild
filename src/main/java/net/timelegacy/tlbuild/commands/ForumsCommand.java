package net.timelegacy.tlbuild.commands;

import net.timelegacy.tlcore.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ForumsCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, @NotNull String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Player Only.");
      return true;
    }

    Player player = (Player) sender;

    MessageUtils.sendMessage(player, "&bhttps://timelegacy.net/", false);
    return false;
  }
}
