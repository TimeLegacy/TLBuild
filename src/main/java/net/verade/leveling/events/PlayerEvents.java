package net.verade.leveling.events;

import net.verade.leveling.LevelPermissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

  @EventHandler
  public void PlayerQuitEvent(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    LevelPermissions.setPlayerPermissions(player);
  }

}