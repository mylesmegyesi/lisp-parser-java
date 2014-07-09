package lava.reader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerNodeTest {

  @Test
  public void toStringPositiveIntNonArbitraryPrecision() {
    IntegerNode node = new IntegerNode(true, "100", false);
    assertEquals("100", node.toString());
  }

  @Test
  public void toStringNegativeIntNonArbitraryPrecision() {
    IntegerNode node = new IntegerNode(false, "100", false);
    assertEquals("-100", node.toString());
  }

  @Test
  public void toStringPositiveIntArbitraryPrecision() {
    IntegerNode node = new IntegerNode(true, "100", true);
    assertEquals("100N", node.toString());
  }

  @Test
  public void toStringNegativeIntArbitraryPrecision() {
    IntegerNode node = new IntegerNode(false, "100", true);
    assertEquals("-100N", node.toString());
  }
}
