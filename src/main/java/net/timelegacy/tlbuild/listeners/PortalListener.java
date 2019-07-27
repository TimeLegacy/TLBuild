package net.timelegacy.tlbuild.listeners;

import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlcore.datatype.AABB3D;
import net.timelegacy.tlcore.datatype.Polyhedron;
import net.timelegacy.tlcore.utils.BungeeUtils;
import net.timelegacy.tlcore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PortalListener implements Listener {

  private final TLBuild plugin;

  public PortalListener(TLBuild plugin) {
    this.plugin = plugin;
  }

  private Polyhedron levelOnePortal = new Polyhedron(-10, 8, -22, 2, 4, 2);
  private Polyhedron levelTwoPortal = new Polyhedron(0, 8, -23, 2, 4, 2);
  private Polyhedron levelThreePortal = new Polyhedron(10, 8, -23, 2, 4, 2);

  private Polyhedron buildServer = new Polyhedron(3.5, 10, 9.5, 1, 2, 1);

  @EventHandler
  public void onPlayerMoveEvent(PlayerMoveEvent event) {
    Player player = event.getPlayer();

    if (Polyhedron.isInside(levelOnePortal, AABB3D.getPlayersAABB(player))) {
      // TODO LATER Send player to level one server.
      //BungeeUtils.sendPlayer(player, "b9596d57-ad35-345d-a9b2-267c028fbf1b");

      if (plugin.getDataManager().getPlayerLevel(player.getUniqueId()) >= 1) {
        player.teleport(Bukkit.getWorld("levelone").getSpawnLocation());
        return;
      }

      player.teleport(event.getPlayer().getWorld().getSpawnLocation());
      player.sendMessage(MessageUtils.colorize("&cYou aren't high enough level."));
      return;
    }

    if (Polyhedron.isInside(levelTwoPortal, AABB3D.getPlayersAABB(player))) {
      // TODO LATER Send player to level one server.
      //BungeeUtils.sendPlayer(player, "b9596d57-ad35-345d-a9b2-267c028fbf1b");

      if (plugin.getDataManager().getPlayerLevel(player.getUniqueId()) >= 2) {
        player.teleport(Bukkit.getWorld("leveltwo").getSpawnLocation());
        return;
      }

      player.teleport(event.getPlayer().getWorld().getSpawnLocation());
      player.sendMessage(MessageUtils.colorize("&cYou aren't high enough level."));
      return;
    }

    if (Polyhedron.isInside(levelThreePortal, AABB3D.getPlayersAABB(player))) {
      // TODO LATER Send player to level one server.
      //BungeeUtils.sendPlayer(player, "b9596d57-ad35-345d-a9b2-267c028fbf1b");

      if (plugin.getDataManager().getPlayerLevel(player.getUniqueId()) >= 3) {
        player.teleport(Bukkit.getWorld("levelthree").getSpawnLocation());
        return;
      }

      player.teleport(event.getPlayer().getWorld().getSpawnLocation());
      player.sendMessage(MessageUtils.colorize("&cYou aren't high enough level."));
      return;
    }

    if (Polyhedron.isInside(buildServer, AABB3D.getPlayersAABB(player))) {
      BungeeUtils.sendPlayer(player, "fc89acb0-3490-4905-9f20-464cc28ce497");
      return;
    }

  }

}
