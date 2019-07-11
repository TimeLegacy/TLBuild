package net.verade;

import java.util.Date;
import net.ess3.api.IEssentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoRestart {

  protected static final Date startupTime = new Date();
  protected static final long restartTime = 86400000; // 24 hours
  protected static IEssentials essentials =
      (IEssentials) Bukkit.getPluginManager().getPlugin("Essentials");

  static void setup() {
    new BukkitRunnable() {

      @Override
      public void run() {
        if (logicBeforeRestartTime()) {
          cancel();
        }
      }
    }.runTaskTimerAsynchronously(TLBuild.getPlugin(), 0, 12000); // 10 Minutes
    Bukkit.getLogger().info("Restart counter started");
  }

  protected static boolean logicBeforeRestartTime() {
    if (startupTime.getTime() + restartTime < new Date().getTime()) {
      new BukkitRunnable() {

        @Override
        public void run() {
          logicAfterRestartTime();
        }
      }.runTaskTimerAsynchronously(TLBuild.getPlugin(), 0, 1200); // 1 Minute
      return true;
    }
    return false;
  }

  protected static void logicAfterRestartTime() {
    if (Bukkit.getServer().getOnlinePlayers().size() == 0) {
      Bukkit.getServer().shutdown();
    } else {
      for (Player player : Bukkit.getServer().getOnlinePlayers()) {
        if (!essentials.getUser(player).isAfk()) {
          return;
        }
      }
      Bukkit.getServer().shutdown();
    }
    Bukkit.getLogger().info("There are active players online");
  }
}
