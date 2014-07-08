package lava.reader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NilNodeTest {

  @Test
  public void toStringNil() throws Exception {
    assertEquals(new NilNode().toString(), "nil");
  }
}
