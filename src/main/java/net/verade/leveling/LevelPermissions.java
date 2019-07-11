package net.verade.leveling;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.timelegacy.tlcore.handler.PermissionHandler;
import net.verade.Build;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LevelPermissions {

  private static List<LevelDatatype> levels = new ArrayList<>();

  public static void setupLevels() {
    //TODO load discoveries from config
    String resourceName = "/levels.json";
    InputStream is = Build.class.getResourceAsStream(resourceName);
    if (is == null) {
      throw new NullPointerException("Cannot find resource file " + resourceName);
    }

    JSONTokener tokener = new JSONTokener(is);
    JSONObject object = new JSONObject(tokener);

    JSONArray levelsJson = object.getJSONArray("levels");
    ArrayList<Integer> levelnum = new ArrayList<>();

    for (int i = 0; i < levelsJson.length(); i++) {
      JSONObject l = levelsJson.getJSONObject(i);

      String worldName = l.getString("worldname");
      int levelNum = l.getInt("levelnumber");

      JSONArray permissionsJson = l.getJSONArray("permissions");
      List<String> permissions = new ArrayList<>();

      for (int p = 0; p < permissionsJson.length(); p++) {
        String permission = permissions.get(i);
        permissions.add(permission);
      }

      levels.add(new LevelDatatype(levelNum, worldName, permissions));
    }
  }

  public static List<String> getPermissions(int levelNumber) {
    List<String> permissions = new ArrayList<>();

    for (LevelDatatype levelDatatype : levels) {
      if (levelDatatype.getLevelNum() == levelNumber) {
        permissions = levelDatatype.getPermissions();
      }
    }

    return permissions;
  }

  public static void setPlayerPermissions(Player player) {
    //TODO get a player's level based on the per player config @piajesse
    List<String> permissions = getPermissions(1);

    for (String perm : permissions) {
      PermissionHandler.addPermission(player, perm);
    }

  }
}
