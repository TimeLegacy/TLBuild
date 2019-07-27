package net.timelegacy.tlbuild.listeners;

import net.timelegacy.tlcore.datatype.AABB3D;
import net.timelegacy.tlcore.datatype.Polyhedron;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

  private Polyhedron levelOnePortal = new Polyhedron(-10, 8, -22, 2, 4, 2);
  private Polyhedron levelTwoPortal = new Polyhedron(0, 8, -23, 2, 4, 2);
  private Polyhedron levelThreePortal = new Polyhedron(10, 8, -23, 2, 4, 2);

  private Polyhedron buildServer = new Polyhedron(3.5, 10, 9.5, 1, 2, 1);

  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    if (Polyhedron.isInside(buildServer, AABB3D.getPlayersAABB(player))) {
      player.teleport(player.getWorld().getSpawnLocation());
    }
  }

}
