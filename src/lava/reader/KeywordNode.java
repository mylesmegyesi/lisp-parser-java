package lava.reader;

public class KeywordNode implements AstNode {
  private SymbolNode symbol;

  KeywordNode(SymbolNode symbol) {
    this.symbol = symbol;
  }

  public String getNamespace() {
    return this.symbol.getNamespace();
  }

  public String getName() {
    return this.symbol.getName();
  }

  public String toString() {
    return this.symbol.toString();
  }
}
