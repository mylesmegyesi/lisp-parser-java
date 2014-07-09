package lava.reader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeywordNodeTest {

  @Test
  public void toStringWithoutNs() throws Exception {
    assertEquals(":a-keyword", new KeywordNode(SymbolNode.fromString("a-keyword")).toString());
  }

  @Test
  public void toStringWithNs() throws Exception {
    assertEquals(":ns/a-keyword", new KeywordNode(SymbolNode.fromString("ns/a-keyword")).toString());
  }
}
