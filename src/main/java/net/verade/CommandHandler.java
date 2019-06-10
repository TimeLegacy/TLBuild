package net.verade;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class CommandHandler implements CommandExecutor {

  private static final String websiteText =
      ("&7&l[&c&l!&8&l] &7&oDon't got one... yet... hehe").replace("&", "§");
  private static final String discordText =
      ("&7&l[&c&l!&8&l] &7&oI got you. Here ya go! \n&bhttps://discord.gg/gMSBFD")
          .replace("&", "§");
  private static final String permErrorText =
      ("&7&l[&c&l!&8&l] &7&oLook's like you don't got permission to that!").replace("&", "§");

  @EventHandler
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (cmd.getName().equals("website")) {
        player.sendMessage(websiteText);
      } else if (cmd.getName().equals("discord")) {
        player.sendMessage(discordText);
      } else if (cmd.getName().equals("pl")) {
        player.sendMessage(permErrorText);
      } else {
        return false;
      }
      return true;
    }
    return false;
  }
}
