package lava.reader;

public class StringNode implements AstNode {
  private String value;

  public StringNode(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
