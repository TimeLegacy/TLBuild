package net.timelegacy.tlbuild;

import net.timelegacy.tlbuild.commands.PlotAliasCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class CommandHandler implements CommandExecutor {

  //TODO move these to the core besides like plotalias obvs

  private static final String websiteText =
      ("&7&l[&c&l!&8&l] &7&oDon't got one... yet... hehe").replace("&", "§");
  private static final String discordText =
      ("&7&l[&c&l!&8&l] &7&oI got you. Here ya go! \n&bhttps://discord.gg/gMSBFD").replace("&", "§");
  private static final String permErrorText =
      ("&7&l[&c&l!&8&l] &7&oLook's like you don't got permission to that!").replace("&", "§");

  @EventHandler
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (!(sender instanceof Player)) {
      return false;
    }

    Player player = (Player) sender;

    if (cmd.getName().equals("plotalias")) {
      return PlotAliasCommand.onCommand(sender, cmd, commandLabel, args);
    }

    return false;
  }
}
