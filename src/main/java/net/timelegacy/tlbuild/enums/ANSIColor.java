package net.timelegacy.tlbuild.enums;

import com.google.common.collect.Maps;
import java.util.Map;

public enum ANSIColor {

  RESET('r', "\u001B[0m"),
  BOLD('l',"\u001b[1m"),
  ITALIC('o',"\u001b[3m"),
  UNDERLINE('n',"\u001b[4m"),
  STRIKETHROUGH('m',"\u001b[9m"),

  BLACK('0',"\u001b[30m"), //&0
  WHITE('f',"\u001b[1;37m"), //&f
  LIGHT_GRAY('7',"\u001b[0;37m"), //&7
  DARK_GRAY('8',"\u001b[1;30m"), //&8

  DARK_RED('4',"\u001b[0;31m"), //&4
  LIGHT_RED('c',"\u001b[1;31m"), //&c

  GREEN('a',"\u001b[1;32m"), //&a
  DARK_GREEN('2',"\u001b[0;32m"), //&2

  YELLOW('e',"\u001b[1;33m"), //&e
  ORANGE('6',"\u001b[0;33m"), //&6

  CYAN('b',"\u001b[1;36m"), //&b
  LIGHT_BLUE('3',"\u001b[0;36m"), //&3
  BLUE('9',"\u001b[1;34m"), //&9
  DARK_BLUE('1',"\u001b[0;34m"), //&1

  MAGENTA('d',"\u001b[1;35m"), //&d
  PURPLE('5',"\u001b[0;35m"), //&5
  ;

  private final char code;
  private final String value;

  private final static Map<String, ANSIColor> BY_ID = Maps.newHashMap();
  private final static Map<Character, ANSIColor> BY_CHAR = Maps.newHashMap();

  ANSIColor(char code, String value) {
    this.code = code;
    this.value = value;
  }

  /**
   * Gets the char value associated with this color
   *
   * @return A char value of this color code
   */
  public char getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  /**
   * Translates a string using an alternate color code character into a
   * string that uses the internal ChatColor.COLOR_CODE color code
   * character. The alternate color code character will only be replaced if
   * it is immediately followed by 0-9, A-F, a-f, K-O, k-o, R or r.
   *
   * @param altColorChar The alternate color code character to replace. Ex: &
   * @param textToTranslate text containing the alternate color code character.
   * @return text containing the ANSIColor.COLOR_CODE color code character.
   */
  public static String translateColorCodes(char altColorChar, String textToTranslate) {
    StringBuilder stringBuilder = new StringBuilder();

    char[] b = textToTranslate.toCharArray();
    for (int i = 0; i < b.length; i++) {
      if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i+1]) > -1) {
        stringBuilder.append(getByChar(b[i+1]).value);

        b[i+1] = Character.DIRECTIONALITY_NONSPACING_MARK;
      }

      stringBuilder.append(b[i]);
    }

    return stringBuilder.toString() + RESET.value;
  }

  public static ANSIColor getByChar(char code) {
    return BY_CHAR.get(code);
  }

  static {
    for (ANSIColor color : values()) {
      BY_ID.put(color.value, color);
      BY_CHAR.put(color.code, color);
    }
  }
}
