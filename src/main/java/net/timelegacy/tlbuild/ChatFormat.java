package net.timelegacy.tlbuild;

import net.timelegacy.tlcore.datatype.Chat;
import net.timelegacy.tlcore.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ChatFormat implements Listener {

  private TLBuild plugin;

  public ChatFormat(TLBuild plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onChat(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    String format = MessageUtils.colorize("&7[&6" + plugin.getDataManager().getPlayerLevel(player.getUniqueId()) + "&7] ");

    Chat.getPlayerChat(player).setPrefix(format);
  }

}
