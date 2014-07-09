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

  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (!this.positive) {
      sb.append("-");
    }
    if (this.intValueAsString.length() == 0) {
      sb.append("0");
    } else {
      sb.append(this.intValueAsString);
    }
    if (this.decimalValueAsString.length() == 0 && this.exponentValueAsString.length() == 0) {
      sb.append("M");
    } else {
      if (this.decimalValueAsString.length() != 0) {
        sb.append(".");
        sb.append(this.decimalValueAsString);
      }
      if (this.exponentValueAsString.length() != 0) {
        sb.append("E");
        if (!this.exponentPositive) {
          sb.append("-");
        }
        sb.append(this.exponentValueAsString);
      }
    }
    return sb.toString();
  }
}
