package net.timelegacy.tlbuild.utils;

import net.timelegacy.tlbuild.enums.ANSIColor;
import org.bukkit.ChatColor;

public class TextUtils {

  public static String colorText(String msg) {
    return ChatColor.translateAlternateColorCodes('&', msg);
  }

  /**
   * Converts the color codes into ANSI color codes.
   * @param msg The message with color codes to convert.
   * */
  public static String translateANSIColorCodes(String msg) {
    return ANSIColor.translateColorCodes('&', msg);
  }

  /**
   * Converts the color codes into ANSI color codes.
   * @param character The character that will be looked for in the conversion.
   * @param msg The message with color codes to convert.
   * */
  public static String translateANSIColorCodes(char character, String msg) {
    return ANSIColor.translateColorCodes(character, msg);
  }
}
