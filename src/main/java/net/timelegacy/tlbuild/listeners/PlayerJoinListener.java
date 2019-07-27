package net.timelegacy.tlbuild.listeners;

import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlbuild.leveling.LevelPermissions;
import net.timelegacy.tlbuild.managers.DataManager;
import net.timelegacy.tlcore.TLCore;
import net.timelegacy.tlcore.handler.RankHandler;
import net.timelegacy.tlcore.utils.MessageUtils;
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
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    int playerLevel = plugin.getDataManager().getPlayerLevel(player.getUniqueId());

    String joinMessage = TLCore.getPlugin().getConfig().getString("join-message")
        .replace("%displayName%", RankHandler.chatColors(player.getUniqueId())
            .replace("%username% &8%arrows%", player.getName()))
        .replace("%level%", "&7[&6" + playerLevel + "&7]");

    TLCore.getPlugin().setJoinMessage(joinMessage);

    event.setJoinMessage(MessageUtils.colorize(TLCore.getPlugin().getJoinMessage()));
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
