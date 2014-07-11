package lava.reader;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SetNodeTest {

  @Test
  public void toStringEmptyList() {
    SetNode node = new SetNode(ImmutableList.<AstNode>of());
    assertEquals("#{}", node.toString());
  }

  @Test
  public void toStringWithOneItem() {
    SymbolNode symbol = SymbolNode.fromString("myapp/thing");
    ImmutableList<AstNode> nodes = ImmutableList.<AstNode>of(symbol);
    SetNode node = new SetNode(nodes);
    assertEquals("#{myapp/thing}", node.toString());
  }

  @Test
  public void toStringWithTwoItems() {
    SymbolNode symbol1 = SymbolNode.fromString("myapp/thing");
    SymbolNode symbol2 = SymbolNode.fromString("myapp/thing2");
    ImmutableList<AstNode> nodes = ImmutableList.<AstNode>of(symbol1, symbol2);
    SetNode node = new SetNode(nodes);
    assertEquals("#{myapp/thing myapp/thing2}", node.toString());
  }

  @Test
  public void toStringWithThreeItems() {
    SymbolNode symbol1 = SymbolNode.fromString("myapp/thing");
    SymbolNode symbol2 = SymbolNode.fromString("myapp/thing2");
    SymbolNode symbol3 = SymbolNode.fromString("myapp/thing3");
    ImmutableList<AstNode> nodes = ImmutableList.<AstNode>of(symbol1, symbol2, symbol3);
    SetNode node = new SetNode(nodes);
    assertEquals("#{myapp/thing myapp/thing2 myapp/thing3}", node.toString());
  }
}

