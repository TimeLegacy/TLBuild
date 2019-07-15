package net.timelegacy.tlbuild.managers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

public interface DataManager {

  void setPlayerLevel(Player player, int level);

  int getPlayerLevel(UUID uuid);

  Map<UUID, UUID> getPlayersReviewing();

  List<UUID> getPlayersNeedingReview();

}
