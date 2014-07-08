package lava.reader;

public class IntegerNode implements Node {
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
}
