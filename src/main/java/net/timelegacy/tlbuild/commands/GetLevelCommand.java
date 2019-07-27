package net.timelegacy.tlbuild.commands;

import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlcore.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetLevelCommand implements CommandExecutor {

  private final TLBuild plugin;

  public GetLevelCommand(TLBuild plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Player Only!");
      return true;
    }

    Player player = (Player) sender;

    player.sendMessage(MessageUtils.colorize("&eYou are currently level &6" + plugin.getDataManager().getPlayerLevel(player.getUniqueId())));
    return false;
  }
}
