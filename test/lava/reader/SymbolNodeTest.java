package lava.reader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SymbolNodeTest {

  @Test
  public void fromStringWithoutNs() throws Exception {
    SymbolNode withoutNs = SymbolNode.fromString("a-symbol");
    assertEquals("a-symbol", withoutNs.getName());
    assertEquals("", withoutNs.getNamespace());
  }

  @Test
  public void fromStringWithNs() throws Exception {
    SymbolNode withoutNs = SymbolNode.fromString("ns/a-symbol");
    assertEquals("a-symbol", withoutNs.getName());
    assertEquals("ns", withoutNs.getNamespace());
  }

  @Test
  public void toStringWithoutNs() throws Exception {
    assertEquals("a-symbol", SymbolNode.fromString("a-symbol").toString());
  }

  @Test
  public void toStringWithNs() throws Exception {
    assertEquals("ns/a-symbol", SymbolNode.fromString("ns/a-symbol").toString());
  }
}
