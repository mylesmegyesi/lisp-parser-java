package lava.reader;

import lava.util.ImmutableArrayList;
import lava.util.ImmutableList;

public class SeenChars {

  private ImmutableList<Character> chars;

  public SeenChars() {
    this.chars = new ImmutableArrayList<Character>();
  }

  private SeenChars(ImmutableList<Character> chars) {
    this.chars = chars;
  }

  public SeenChars add(char c) {
    return new SeenChars(this.chars.append(c));
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Character c : chars) {
      sb.append(c);
    }
    return sb.toString();
  }
}
