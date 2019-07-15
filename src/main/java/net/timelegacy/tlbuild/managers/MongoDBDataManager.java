package net.timelegacy.tlbuild.managers;

import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.timelegacy.tlcore.mongodb.MongoDB;
import org.bson.Document;
import org.bukkit.entity.Player;

public class MongoDBDataManager implements DataManager {

  private Map<UUID, UUID> playersReviewing = new HashMap<>();
  private List<UUID> playersNeedingReview = new ArrayList<>();

  private MongoCollection<Document> players = MongoDB.mongoDatabase.getCollection("players");

  public MongoDBDataManager() {
    for (String s : MongoDB.mongoDatabase.listCollectionNames()) {
      if (s.equalsIgnoreCase("test")) {
        break;
      } else {
        MongoDB.mongoDatabase.createCollection("test");
        break;
      }
    }

  }

  @Override
  public void setPlayerLevel(Player player, int level) {
  }

  @Override
  public int getPlayerLevel(UUID uuid) {
    int level = 1;

////    if (!PlayerHandler.playerExists(player.getUniqueId())) {
////      PlayerHandler.createPlayer(Bukkit.getPlayer(player.getUniqueId()));
////      return level;
////    }
//
//    FindIterable<Document> doc = players.find(Filters.eq("uuid", player.getUniqueId().toString()));
//
//    level = doc.first().getInteger("level");

    return level;
  }


  public void getReview() {

  }

  public void setReveiw() {

  }

  @Override
  public Map<UUID, UUID> getPlayersReviewing() {
    return playersReviewing;
  }

  @Override
  public List<UUID> getPlayersNeedingReview() {
    return playersNeedingReview;
  }
}
