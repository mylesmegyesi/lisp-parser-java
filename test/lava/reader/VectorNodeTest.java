package lava.reader;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VectorNodeTest {

  @Test
  public void toStringEmptyList() {
    ListNode node = new ListNode(ImmutableList.<AstNode>of());
    assertEquals("()", node.toString());
  }

  @Test
  public void toStringWithOneItem() {
    SymbolNode symbol = SymbolNode.fromString("myapp/thing");
    ImmutableList<AstNode> nodes = ImmutableList.<AstNode>of(symbol);
    VectorNode node = new VectorNode(nodes);
    assertEquals("[myapp/thing]", node.toString());
  }

  @Test
  public void toStringWithTwoItems() {
    SymbolNode symbol1 = SymbolNode.fromString("myapp/thing");
    SymbolNode symbol2 = SymbolNode.fromString("myapp/thing2");
    ImmutableList<AstNode> nodes = ImmutableList.<AstNode>of(symbol1, symbol2);
    VectorNode node = new VectorNode(nodes);
    assertEquals("[myapp/thing myapp/thing2]", node.toString());
  }

  @Test
  public void toStringWithThreeItems() {
    SymbolNode symbol1 = SymbolNode.fromString("myapp/thing");
    SymbolNode symbol2 = SymbolNode.fromString("myapp/thing2");
    SymbolNode symbol3 = SymbolNode.fromString("myapp/thing3");
    ImmutableList<AstNode> nodes = ImmutableList.<AstNode>of(symbol1, symbol2, symbol3);
    VectorNode node = new VectorNode(nodes);
    assertEquals("[myapp/thing myapp/thing2 myapp/thing3]", node.toString());
  }
}
