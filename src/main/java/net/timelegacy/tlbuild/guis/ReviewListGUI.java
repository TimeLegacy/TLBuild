package net.timelegacy.tlbuild.guis;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlbuild.managers.DataManager;
import net.timelegacy.tlcore.utils.ItemUtils;
import net.timelegacy.tlcore.utils.MenuUtils;
import net.timelegacy.tlcore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ReviewListGUI implements Listener {

  private final TLBuild plugin;

  public ReviewListGUI(TLBuild plugin) {
    this.plugin = plugin;
  }

  public void openGUI(Player player, int page) {
    Inventory inv = Bukkit.createInventory(player, 9 * 5, MessageUtils.colorize("&8&lReview List >> &8&nPage " + page));

    // Row 1
    inv.setItem(4, ItemUtils.createNMSSkullItem(ItemUtils.createSkullItem("player"),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E0OTJmZmY1M2M0N2I1ZWMzODhhYWVlNTZhZGE3ZjRjNjBiNjU1NzZiNDE2MWQ2NmY1M2I1ZTYzMDE3YmQifX19",
        "Review List Icon"));

    // Row 6
    inv.setItem(39, ItemUtils.createItem(Material.ARROW, 1, "&aPrevious Page"));
    inv.setItem(40, ItemUtils.createItem(Material.STRUCTURE_VOID, 1, "&cClose"));
    inv.setItem(41, ItemUtils.createItem(Material.ARROW, 1, "&aNext Page"));

    player.openInventory(inv);

    if (plugin.getDataManager().getPlayersNeedingReview().size() == 0) {
      inv.setItem(22, ItemUtils
          .createItem(Material.BARRIER, "&cEmpty", Arrays.asList("&7No players are in", "&7need of a review!")));
      return;
    }

    new BukkitRunnable() {

      DataManager dataManager = plugin.getDataManager();

      @Override
      public void run() {
        int start = (page * 21) - 21;
        int forgotten = 0;

        for (int i = 10; i <= 34; i++) {
          if (i == 17 || i == 18 || i == 26 || i == 27) {
            forgotten++;
            continue;
          }

          int current = ((i - 10) + start) - forgotten;

          if (current >= dataManager.getPlayersNeedingReview().size()) {
            continue;
          }

          if (dataManager.getPlayersUnderReview().containsValue(dataManager.getPlayersNeedingReview().get(current))) {
            continue;
          }

          UUID uuid = dataManager.getPlayersNeedingReview().get(current);
          String playerName = Bukkit.getOfflinePlayer(uuid).getName();

          ItemStack itemStack = ItemUtils.createSkullItem(playerName);
          ItemMeta itemMeta = itemStack.getItemMeta();

          itemMeta.setDisplayName(MessageUtils.colorize("&a" + playerName));

          List<String> lore = Arrays.asList(
              "&eLevel&8: &7&l[&6" + dataManager.getPlayerLevel(uuid) + "&7&l]",
              "&eSubmission Date&8: ",
              "&eLast Seen&8: ");

          itemMeta.setLore(MessageUtils.colorList(lore));
          itemStack.setItemMeta(itemMeta);

          ItemStack finalItem = ItemUtils.addLocalizedName(itemStack, uuid.toString());

          inv.setItem(i, finalItem);

          player.updateInventory();
        }
      }
    }.runTaskAsynchronously(plugin);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();

    if (event.getCurrentItem() == null) {
      return;
    }

    if (event.getCurrentItem().getType() == Material.AIR) {
      return;
    }

    String title = ChatColor.stripColor(event.getInventory().getTitle()).replace(" ", "");

    if (!title.startsWith("ReviewList>>Page")) {
      return;
    }

    event.setCancelled(true);

    if (event.getCurrentItem().getType() == Material.STRUCTURE_VOID) {
      player.closeInventory();
      return;
    }

    int pageNumber = Integer.parseInt(title.split("Page")[1]);

    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageUtils.colorize("&aPrevious Page"))) {
      if (pageNumber == 1) {
        MenuUtils.displayGUIError(event.getInventory(), event.getSlot(), event.getCurrentItem(),
            ItemUtils.createItem(Material.BARRIER, 1, "&cThis is the first page!"), 3);
      } else {
        openGUI(player, pageNumber - 1);
      }

      return;
    }

    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageUtils.colorize("&aNext Page"))) {
      double pages = (double) plugin.getDataManager().getPlayersNeedingReview().size() / 21;

      if (pageNumber == MenuUtils.roundUp(pages)) {
        MenuUtils.displayGUIError(
            event.getInventory(),
            event.getSlot(),
            event.getCurrentItem(),
            ItemUtils.createItem(Material.BARRIER, 1, "&cThis is the last page!"), 3);
      } else {
        openGUI(player, pageNumber + 1);
      }

      return;
    }

    for (UUID uuid : plugin.getDataManager().getPlayersNeedingReview()) {
      if (event.getCurrentItem().getItemMeta().getLocalizedName().equals(uuid.toString())) {
        System.out.println("UUID: " + uuid.toString());
        if (uuid == player.getUniqueId()) {
          player.sendMessage("You can't review your own plot!");
          break;
        }

        plugin.getDataManager().putPlayerUnderReview(player.getUniqueId(), uuid);

        player.sendMessage("You are now reviewing " + Bukkit.getOfflinePlayer(uuid).getName() + "'s plot!");
        player.teleport(plugin.getDataManager().getPlayerSubmissionLocation(uuid));
        break;
        // Open confirmation GUI.
      }
    }

//    for (CustomPlayerData playerData : plugin.getDataManager().getPlayersNeedingReview()) {
//      if (event.getCurrentItem().getItemMeta().getLocalizedName().equals(playerData.getLocalizedName())) {
//        plugin.getDataManager().getPlayersUnderReview().put(event.getWhoClicked().getUniqueId(), playerData.getUuid());
//      }
//    }

//    for (UUID playerToReview : plugin.getPlotsUnderReview()) {
//      if (event.getCurrentItem().getItemMeta().getLocalizedName().equals(playerToReview.getLocalizedName())) {
//
//        MessageUtils.sendMessage(playerToReview, "&eYou are now reviewing " + playerToReview.getDisplayName(), false);
//        player.updateInventory();
//        player.closeInventory();
//        break;
//      }
//    }

  }

}
