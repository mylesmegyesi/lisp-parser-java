package lava.reader;

public class IntegerNode implements AstNode {
  private String value;
  private boolean arbitraryPrecision;
  private boolean positive;

  IntegerNode(boolean positive, String value, boolean arbitraryPrecision) {
    this.positive = positive;
    this.value = value;
    this.arbitraryPrecision = arbitraryPrecision;
  }

  public String getValueAsString() {
    return this.value;
  }

  public boolean isArbitraryPrecision() {
    return arbitraryPrecision;
  }

  public boolean isPositive() {
    return this.positive;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (this.positive) {
      sb.append(this.value);
    } else {
      sb.append("-");
      sb.append(this.value);
    }
    if (this.arbitraryPrecision) {
      sb.append("N");
    }
    return sb.toString();
  }
}
