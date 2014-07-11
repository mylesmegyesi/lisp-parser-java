package lava.reader;

import com.google.common.collect.ImmutableList;

public class SeenChars {

  private ImmutableList<Character> chars;

  public SeenChars() {
    this.chars = ImmutableList.<Character>of();
  }

  private SeenChars(ImmutableList<Character> chars) {
    this.chars = chars;
  }

  public SeenChars add(char c) {
    ImmutableList.Builder<Character> builder = ImmutableList.<Character>builder();
    return new SeenChars(builder.addAll(this.chars).add(c).build());
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Character c : chars) {
      sb.append(c);
    }
    return sb.toString();
  }
}
