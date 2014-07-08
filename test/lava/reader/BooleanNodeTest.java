package lava.reader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BooleanNodeTest {

  @Test
  public void toStringTrue() throws Exception {
    assertEquals(new BooleanNode(true).toString(), "true");
  }

  @Test
  public void toStringFalse() throws Exception {
    assertEquals(new BooleanNode(false).toString(), "false");
  }

}
