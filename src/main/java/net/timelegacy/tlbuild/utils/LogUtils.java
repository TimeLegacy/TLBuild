package net.timelegacy.tlbuild.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import net.timelegacy.tlbuild.TLBuild;
import org.bukkit.plugin.java.JavaPlugin;

public final class LogUtils {

  private static TLBuild plugin = JavaPlugin.getPlugin(TLBuild.class);
  private static final String consolePrefix = "&7[&3Hugs&7] ";
  private static final String consoleWarningPrefix = "&7[&eHugs&7] ";
  private static final String consoleErrorPrefix = "&7[&cHugs&7] ";

  private LogUtils(TLBuild plugin) {
    LogUtils.plugin = plugin;
  }

  private static Logger getLogger() {
    return plugin.getLogger().getParent();
  }

  public static void logInfo(String msg) {
    getLogger().info(TextUtils.translateANSIColorCodes(getConsolePrefix() + msg));
  }

  public static void logInfoNoPrefix(String msg) {
    getLogger().info(TextUtils.translateANSIColorCodes(msg));
  }

  public static void logWarning(String msg) {
    getLogger().warning(TextUtils.translateANSIColorCodes(getConsoleWarningPrefix() + msg));
  }

  public static void logWarningNpPrefix(String msg) {
    getLogger().warning(TextUtils.translateANSIColorCodes(msg));
  }

  public static void logError(String msg) {
    getLogger().severe(TextUtils.translateANSIColorCodes(getConsoleErrorPrefix() + msg));
  }

  public static void logErrorNoPrefix(String msg) {
    getLogger().severe(TextUtils.translateANSIColorCodes(msg));
  }

  public static void logToFile(String message) {
    final DateFormat fileDateFormat = new SimpleDateFormat("yyyy EEE, MMMMM dd");
    final Date d = new Date();
    final String date = fileDateFormat.format(d);
    final DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

    String newMessage;
    newMessage = "[" + dateFormat.format(d) + "] " + message;

    try {
      final File file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "logs", date + ".txt");
      final File parent = file.getParentFile();
      if (!parent.exists()) {
        parent.mkdirs();
      }
      final FileWriter fw = new FileWriter(file, true);
      final PrintWriter pw = new PrintWriter(fw);
      pw.println(newMessage);
      pw.flush();
      pw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String getConsolePrefix() {
    return "[TLBuild]";
  }

  private static String getConsoleWarningPrefix() {
    return consoleWarningPrefix;
  }

  private static String getConsoleErrorPrefix() {
    return consoleErrorPrefix;
  }
}