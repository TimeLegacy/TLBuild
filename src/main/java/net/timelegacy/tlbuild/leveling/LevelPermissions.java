package net.timelegacy.tlbuild.leveling;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlcore.handler.PermissionHandler;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LevelPermissions {

  private static List<LevelDatatype> levels = new ArrayList<>();

  public static void setupLevels() {
    //TODO load discoveries from config
    String resourceName = "/levels.json";
    InputStream is = TLBuild.class.getResourceAsStream(resourceName);
    if (is == null) {
      throw new NullPointerException("Cannot find resource file " + resourceName);
    }

    JSONTokener tokener = new JSONTokener(is);
    JSONObject object = new JSONObject(tokener);

    JSONArray levelsJson = object.getJSONArray("levels");

    for (int i = 0; i < levelsJson.length(); i++) {
      JSONObject l = levelsJson.getJSONObject(i);

      String worldName = l.getString("worldname");
      int levelNum = l.getInt("levelnumber");

      JSONArray permissionsJson = l.getJSONArray("permissions");
      List<String> permissions = new ArrayList<>();

      //DEBUG
      System.out.println(l.toString() + " " + permissionsJson.toString() + " length: " + permissionsJson.length());

      for (int p = 0; p < permissionsJson.length(); p++) {
        String permission = permissionsJson.getString(p);
        permissions.add(permission);
      }

      levels.add(new LevelDatatype(levelNum, worldName, permissions));
    }
  }

  public static List<String> getPermissions(int levelNumber) {
    List<String> permissions = new ArrayList<>();

    for (LevelDatatype levelDatatype : levels) {
      if (levelDatatype.getLevelNum() == levelNumber) {
        for (String perm : levelDatatype.getPermissions()) {
          permissions.add(perm);
        }
        for (LevelDatatype levelInherit : levels) {
          if (levelInherit.getLevelNum() < levelNumber) {
            for (String perm : levelInherit.getPermissions()) {
              permissions.add(perm);
            }
          }
        }

        break;
      }
    }

    return permissions;
  }

  public static void setPlayerPermissions(Player player, int level) {
    for (String perm : getPermissions(level)) {
      PermissionHandler.addPermission(player, perm);
    }
  }
}