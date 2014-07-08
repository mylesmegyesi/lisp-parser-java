package lava.reader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SymbolNodeTest {

  @Test
  public void fromStringWithoutNs() throws Exception {
    SymbolNode withoutNs = SymbolNode.fromString("a-symbol");
    assertEquals(withoutNs.getNamespace(), "");
    assertEquals(withoutNs.getName(), "a-symbol");
  }

  @Test
  public void fromStringWithNs() throws Exception {
    SymbolNode withoutNs = SymbolNode.fromString("ns/a-symbol");
    assertEquals(withoutNs.getNamespace(), "ns");
    assertEquals(withoutNs.getName(), "a-symbol");
  }

  @Test
  public void toStringWithoutNs() throws Exception {
    assertEquals(SymbolNode.fromString("a-symbol").toString(), "a-symbol");
  }

  @Test
  public void toStringWithNs() throws Exception {
    assertEquals(SymbolNode.fromString("ns/a-symbol").toString(), "ns/a-symbol");
  }
}
