package net.timelegacy.tlbuild.commands;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotAliasCommand {

  public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      return false;
    }

    Player player = (Player) sender;
    Plot plot = PlotPlayer.get(player.getName()).getCurrentPlot();

    if (plot == null) {
      player.sendMessage("§7§l[§cTAC§8§l] §cWhat, what are you doing? You can't name the roads!");
      return true;
    }

    if (!plot.getOwners().contains(player.getUniqueId())) {
      player.sendMessage("§7§l[§cTAC§8§l] §cOn a Plot, Check! Owns Plot... What are you trying to pull?");
      return true;
    }

    if (args.length > 0) {
      StringBuilder alias = new StringBuilder();
      for (String s : args) {
        alias.append(" ").append(s);
      }
      plot.setAlias(alias.toString());
    } else {
      player.sendMessage("§7§l[§cTAC§8§l] §cI'm not gonna name it to nothing, try one of the following...");
      player.sendMessage("§7/plotalias This is a Cool Plot");
      player.sendMessage("§7/plotalias TacPalace");
    }

    return true;
  }
}
