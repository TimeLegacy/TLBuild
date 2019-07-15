package net.timelegacy.tlbuild.leveling.player;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.UUID;
import net.timelegacy.tlcore.handler.ServerHandler;
import net.timelegacy.tlcore.mongodb.MongoDB;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerStorage {

  private static MongoCollection<Document> reviewPlots =
      MongoDB.mongoDatabase.getCollection("creative_leveling_system");

  public static void submitPlot(Player player, Location location, String plotName) {
    reviewPlots.insertOne(
        new Document("player_uuid", player.getUniqueId().toString())
            .append("status", "posted")
            .append("server", ServerHandler.getServerUUID().toString())
            .append("plot_name", plotName)
            .append("location_x", location.getX())
            .append("location_z", location.getZ())
            .append("location_world", location.getWorld()));
  }

  public static void updatePlotStatus(Location location, String status) {
    reviewPlots.updateOne(Filters.eq("_id", getObjectID(location)), new Document("$set", new Document("status", status)));
  }

  public static void updatePlot(Location location, String status, String comment, UUID commenter) {
    updatePlotStatus(location, status);
  }

  public static String checkStatus(Location location) {
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

  public static String getComment(Location location) {
    FindIterable<Document> documents = reviewPlots.find(Filters.eq("location_x", location.getX()));
    for (Document document : documents) {
      if (document.getInteger("location_z").equals(location.getZ())) {
        if (document.getInteger("location_world").equals(location.getWorld())) {
          if (document.getString("server").equals(ServerHandler.getServerUUID().toString())) {
            return document.getString("comment");
          }
        }
      }
    }

    return "missing";
  }

  public static Location[] getPlots(Player player) {
    FindIterable<Document> documents = reviewPlots.find(Filters.eq("player_uuid", player.getUniqueId().toString()));
    ArrayList<Location> response = new ArrayList<>();
    for (Document document : documents) {
      if (document.getString("server").equals(ServerHandler.getServerUUID().toString())) {
        response.add(new Location(
            Bukkit.getWorld(document.getString("location_world")),
            document.getInteger("location_x"),
            64,
            document.getInteger("location_z")));
      }
    }

    return response.toArray(new Location[response.size()]);
  }

  protected static ObjectId getObjectID(Location location) {
    FindIterable<Document> documents = reviewPlots.find(Filters.eq("location_x", location.getX()));
    for (Document document : documents) {
      if (document.getInteger("location_z").equals(location.getZ())) {
        if (document.getInteger("location_world").equals(location.getWorld())) {
          if (document.getString("server").equals(ServerHandler.getServerUUID().toString())) {
            return document.getObjectId("_id");
          }
        }
      }
    }

    return null;
  }

}
