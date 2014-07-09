package lava.reader;

public class FloatNode implements AstNode {

  private boolean positive;
  private boolean exponentPositive;
  private String intValueAsString;
  private String decimalValueAsString;
  private String exponentValueAsString;

  FloatNode(boolean positive, String intValueAsString, String decimalValueAsString, String exponentValueAsString, boolean exponentPositive) {
    this.positive = positive;
    this.intValueAsString = intValueAsString;
    this.decimalValueAsString = decimalValueAsString;
    this.exponentValueAsString = exponentValueAsString;
    this.exponentPositive = exponentPositive;
  }

  public String getIntValueAsString() {
    return this.intValueAsString;
  }

  public String getDecimalValueAsString() {
    return this.decimalValueAsString;
  }

  public String getExponentValueAsString() {
    return this.exponentValueAsString;
  }

  public boolean isPositive() {
    return positive;
  }

  public boolean isExponentPositive() {
    return exponentPositive;
  }
}
