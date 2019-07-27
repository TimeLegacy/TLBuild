package net.timelegacy.tlbuild.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.timelegacy.tlbuild.TLBuild;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPIHook extends PlaceholderExpansion {

  // Instance of plugin we want to use.
  private TLBuild plugin;

  public PlaceholderAPIHook(TLBuild plugin) {
    this.plugin = plugin;
  }

  @Override
  public String onRequest(OfflinePlayer player, String identifier) {
    // %example_placeholder1%
    if (identifier.equals("levelone")) {
      if (plugin.getDataManager().getPlayerLevel(player.getUniqueId()) >= 1) {
        return "&a✔ Accessible";
      } else {
        return "&c✘ Rankup to Access";
      }
    }

    if (identifier.equals("leveltwo")) {
      if (plugin.getDataManager().getPlayerLevel(player.getUniqueId()) >= 2) {
        return "&a✔ Accessible";
      } else {
        return "&c✘ Rankup to Access";
      }
    }

    if (identifier.equals("levelthree")) {
      if (plugin.getDataManager().getPlayerLevel(player.getUniqueId()) >= 3) {
        return "&a✔ Accessible";
      } else {
        return "&c✘ Rankup to Access";
      }
    }

    return null;
  }

  @Override
  public String getIdentifier() {
    return "tlbuild";
  }

  @Override
  public String getAuthor() {
    return "TimeLegacy";
  }

  @Override
  public String getVersion() {
    return "1.0";
  }
}
