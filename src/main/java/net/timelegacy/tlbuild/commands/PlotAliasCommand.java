package net.timelegacy.tlbuild.commands;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotAliasCommand {

  public static boolean onCommand(
      CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;

      Plot plot = PlotPlayer.get(player.getName()).getCurrentPlot();
      if (plot != null) {
        if (plot.getOwners().contains(player.getUniqueId())) {
          if (args.length > 0) {
            String alias = "";
            for (String s : args) {
              alias = alias + " " + s;
            }
            plot.setAlias(alias);
          } else {
            player.sendMessage(
                "§7§l[§cTAC§8§l] §cI'm not gonna name it to nothing, try one of the following...");

            player.sendMessage("§7/plotalias This is a Cool Plot");
            player.sendMessage("§7/plotalias TacPalace");
          }
        } else {
          player.sendMessage(
              "§7§l[§cTAC§8§l] §cOn a Plot, Check! Owns Plot... What are you trying to pull?");
        }
      } else {
        player.sendMessage("§7§l[§cTAC§8§l] §cWhat, what are you doing? You can't name the roads!");
      }
    }
    return true;
  }
}
