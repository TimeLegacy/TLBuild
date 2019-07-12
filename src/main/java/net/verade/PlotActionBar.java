package net.verade;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import java.util.UUID;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlotActionBar {

  static void setup() {
    new BukkitRunnable() {

      @Override
      public void run() {
        logic();
      }
    }.runTaskTimerAsynchronously(TLBuild.getPlugin(), 0, 1); // Every tick!
  }

  protected static void logic() {
    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      Plot plot = PlotPlayer.get(player.getDisplayName()).getCurrentPlot();
      if (plot != null) {
        String message;
        if (plot.getAlias().equals("")) {
          message = "§6§lPLOT OWNED§7 by §b";
        } else {
          message = "§6§l" + plot.getAlias() + "§7 by §b";
        }
        boolean notFirst = false;
        for (UUID u : plot.getOwners()) {
          if (notFirst) {
            message = message + "§7, §b";
          }
          Bukkit.getOfflinePlayer(u).getName();
          notFirst = true;
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

      } else {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
      }
    }
  }
}
