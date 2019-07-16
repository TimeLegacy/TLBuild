package net.timelegacy.tlbuild.managers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.timelegacy.tlbuild.leveling.CustomPlayerReview;
import net.timelegacy.tlcore.handler.ServerHandler;
import net.timelegacy.tlcore.mongodb.MongoDB;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MongoDBDataManager implements DataManager {

  private Map<UUID, UUID> playersReviewing = new HashMap<>();
  private List<UUID> playersNeedingReview = new ArrayList<>();

  private MongoCollection<Document> reviewPlots = MongoDB.mongoDatabase.getCollection("creative_leveling_system");

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
  public void addPlayer(UUID uuid, Location location, Date dateSubmitted, Date lastSeen) {
    new CustomPlayerReview(uuid, getPlayerLevel(uuid), location, dateSubmitted, lastSeen);
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

//    FindIterable<Document> doc = reviewPlots.find(Filters.eq("uuid", uuid));
//
//    level = doc.first().getInteger("level");

    return level;
  }

  @Override
  public void submitPlot(Player player, Location location, int level) {
    reviewPlots.insertOne(
        new Document("player_uuid", player.getUniqueId().toString())
            .append("level", level)
            .append("status", "posted")
            .append("server", ServerHandler.getServerUUID().toString())
            .append("location_x", location.getX())
            .append("location_z", location.getZ())
            .append("location_world", location.getWorld()));
  }

  @Override
  public String checkStatus(Location location) {
    FindIterable<Document> documents = reviewPlots.find(Filters.eq("location_x", location.getX()));
    for (Document document : documents) {
      if (document.getInteger("location_z").equals(location.getZ())) {
        if (document.getInteger("location_world").equals(location.getWorld())) {
          if (document.getString("server").equals(ServerHandler.getServerUUID().toString())) {
            return document.getString("status");
          }
        }
      }
    }

    return "missing";
  }


  public void getReview() {

  }

  public void setReveiw() {

  }

  @Override
  public Map<UUID, UUID> getPlayersUnderReview() {
    return playersReviewing;
  }

  @Override
  public List<UUID> getPlayersNeedingReview() {
    return playersNeedingReview;
  }
}
