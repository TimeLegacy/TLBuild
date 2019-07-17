package net.timelegacy.tlbuild.events;

import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlbuild.leveling.LevelPermissions;
import net.timelegacy.tlbuild.managers.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

  private final TLBuild plugin;

  public PlayerJoinListener(TLBuild plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    DataManager dataManager = plugin.getDataManager();

    if (!dataManager.playerExists(event.getPlayer().getUniqueId())) {
      dataManager.createPlayer(event.getPlayer().getUniqueId());
    }

    LevelPermissions.setPlayerPermissions(player, dataManager.getPlayerLevel(player.getUniqueId()));
  }

}
