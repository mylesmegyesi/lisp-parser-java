package lava.reader;

public class BooleanNode implements Node {

  private boolean value;

  public BooleanNode(boolean value) {
    this.value = value;
  }

  public boolean getValue() {
    return this.value;
  }

  public String toString() {
    if (this.value) {
      return "true";
    } else {
      return "false";
    }
  }
}
