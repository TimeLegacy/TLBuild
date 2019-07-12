package net.verade.leveling.events;

import net.verade.leveling.LevelPermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

  @EventHandler
  public void PlayerQuitEvent(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    LevelPermissions.setPlayerPermissions(player);

    if (player.hasPermission("test.permission1")) {
      Bukkit.broadcastMessage("yes 1");
    }

    if (player.hasPermission("test.permission4")) {
      Bukkit.broadcastMessage("yes 2");
    }

    if (player.hasPermission("test.permission7")) {
      Bukkit.broadcastMessage("yes 3");
    }
  }

}
