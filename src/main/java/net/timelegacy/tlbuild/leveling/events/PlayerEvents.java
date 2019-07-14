package net.timelegacy.tlbuild.leveling.events;

import net.timelegacy.tlbuild.leveling.LevelPermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

  @EventHandler
  public void PlayerQuitEvent(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    LevelPermissions.setPlayerPermissions(player, 3);

    if (player.hasPermission("fawe.worldeditregion")) {
      Bukkit.broadcastMessage("level 1");
    }

    if (player.hasPermission("worldedit.removenear")) {
      Bukkit.broadcastMessage("level 2");
    }

    if (player.hasPermission("worldedit.region.naturalize")) {
      Bukkit.broadcastMessage("level 3");
    }

  }

}
