package net.timelegacy.tlbuild.guis;

import java.net.http.WebSocket.Listener;
import java.util.UUID;
import net.timelegacy.tlbuild.TLBuild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ConfirmationGUI implements Listener {

  private final TLBuild plugin;

  public ConfirmationGUI(TLBuild plugin) {
    this.plugin = plugin;
  }

  public void openGUI(Player player, Player target) {
    Inventory inv = Bukkit.getServer().createInventory(null, 9 * 3, "");

    player.openInventory(inv);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();

    plugin.getDataManager().getPlayersUnderReview().put(player.getUniqueId(), UUID.fromString(event.getCurrentItem().getItemMeta().getLocalizedName()));
  }
}
