package net.timelegacy.tlbuild.managers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface DataManager {

  void setPlayerLevel(Player player, int level);

  int getPlayerLevel(UUID uuid);

  void submitPlot(Player player, Location location, int level);

  String checkStatus(Location location);

  Map<UUID, UUID> getPlayersUnderReview();

  List<UUID> getPlayersNeedingReview();

}
