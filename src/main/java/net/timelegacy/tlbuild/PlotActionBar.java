package net.timelegacy.tlbuild;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import java.util.HashMap;
import java.util.UUID;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlotActionBar {

  protected static HashMap<UUID, String> playersLastMessage = new HashMap<>();

  static void setup() {
    new BukkitRunnable() {
      public void run() {
        logic();
      }
    }.runTaskTimer(TLBuild.getPlugin(), 0L, 20L);
  }

  protected static void logic() {
    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      if (playersLastMessage.get(player.getUniqueId()) == null) {
        moveEvent(new PlayerMoveEvent(player, null, null));
      }
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
          TextComponent.fromLegacyText(playersLastMessage.get(player.getUniqueId())));
    }
  }

  static void moveEvent(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    Plot plot = PlotPlayer.get(player.getName()).getCurrentPlot();
    String message;
    if (plot == null) {
      message = "";
    } else if (plot.getOwners().contains(player.getUniqueId())) {
      if (plot.getAlias().equals("")) {
        message = "§6§lYour Plot §f» §7/plotalias <your plot name>";
      } else {
        message = "§6§l" + plot.getAlias();
      }
    } else {
      if (plot.getAlias().equals("")) {
        message = "§6§lPLOT OWNED§7 by §b";
      } else {
        message = "§6§l" + plot.getAlias() + "§7 by §b";
      }

      boolean notFirst = false;
      for (java.util.UUID u : plot.getOwners()) {
        if (notFirst) {
          message = message + "§7, §b";
        }
        message = message + Bukkit.getOfflinePlayer(u).getName();
        notFirst = true;
      }

      if (!notFirst) {
        message = "§C§lNOT CLAIMED §f» §7/plot claim";
      }
    }

    if (playersLastMessage.get(player.getUniqueId()) == null) {
      playersLastMessage.put(player.getUniqueId(), message);
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    } else {
      if (!playersLastMessage.get(player.getUniqueId()).equals(message)) {
        playersLastMessage.put(player.getUniqueId(), message);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
      }
    }
  }

  static void playerLeave(PlayerQuitEvent event) {
    playersLastMessage.remove(event.getPlayer().getUniqueId());
  }
}
