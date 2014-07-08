package lava.reader;

public class SymbolNode implements Node {
  private String namespace;
  private String name;

  public SymbolNode(String namespace, String name) {
    this.namespace = namespace;
    this.name = name;
  }

  public static SymbolNode fromString(String s) {
    int slashIndex = s.indexOf('/');
    if (slashIndex == -1) {
      return new SymbolNode("", s);
    } else {
      return new SymbolNode(s.substring(0, slashIndex), s.substring(slashIndex + 1, s.length()));
    }
  }

  public String toString() {
    if (this.namespace == "") {
      return this.name;
    } else {
      return this.namespace + "/" + this.name;
    }
  }

  public String getNamespace() {
    return namespace;
  }

  public String getName() {
    return name;
  }
}
