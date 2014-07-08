package lava.reader;

public class Util {
  public static boolean isWhitespace(char c) {
    return Character.isWhitespace(c) || c == ',';
  }
}
