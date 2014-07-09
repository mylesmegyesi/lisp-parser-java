package lava.reader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FloatNodeTest {

  @Test
  public void toStringPositiveWithNoDecimalAndNoExponent() {
    FloatNode node = new FloatNode(true, "100", "", "", true);
    assertEquals("100M", node.toString());
  }

  @Test
  public void toStringNegativeWithNoDecimalAndNoExponent() {
    FloatNode node = new FloatNode(false, "100", "", "", true);
    assertEquals("-100M", node.toString());
  }

  @Test
  public void toStringPositiveWithDecimal() {
    FloatNode node = new FloatNode(true, "100", "200", "", true);
    assertEquals("100.200", node.toString());
  }

  @Test
  public void toStringPositiveWithDecimalAndPositiveExponent() {
    FloatNode node = new FloatNode(true, "100", "200", "300", true);
    assertEquals("100.200E300", node.toString());
  }

  @Test
  public void toStringPositiveWithDecimalAndNegativeExponent() {
    FloatNode node = new FloatNode(true, "100", "200", "300", false);
    assertEquals("100.200E-300", node.toString());
  }

  @Test
  public void toStringPositiveNoDecimalWithPositiveExponent() {
    FloatNode node = new FloatNode(true, "100", "", "300", true);
    assertEquals("100E300", node.toString());
  }

  @Test
  public void toStringNoIntValueWithDecimal() {
    FloatNode node = new FloatNode(true, "", "100", "", true);
    assertEquals("0.100", node.toString());
  }
}
