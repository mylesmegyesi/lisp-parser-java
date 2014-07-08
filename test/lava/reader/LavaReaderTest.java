package lava.reader;

import lava.util.ImmutableList;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class LavaReaderTest {

  private ImmutableList<Node> readString(String s) {
    LavaReader reader = new LavaReader();
    ReadResult result = reader.readString(s);
    return result.getNodes();
  }

  private Node readStringFirstNode(String s) throws Exception {
    ImmutableList<Node> nodes = readString(s);
    if (nodes.size() == 1) {
      return nodes.first();
    } else {
      fail("Tried read first node of string but multiple nodes read: " + nodes.toString());
    }
    return null;
  }

  @Test
  public void readsAnEmptyStringAsNothing() throws Exception {
    ImmutableList<Node> nodes = readString("");
    assertEquals(nodes.size(), 0);
  }

  @Test
  public void readsASpaceAsNothing() throws Exception {
    ImmutableList<Node> nodes = readString(" ");
    assertEquals(nodes.size(), 0);
  }

  @Test
  public void readsMultipleSpacesAsNothing() throws Exception {
    ImmutableList<Node> nodes = readString("  ");
    assertEquals(nodes.size(), 0);
  }

  @Test
  public void readsAUnixNewlineAsNothing() throws Exception {
    ImmutableList<Node> nodes = readString("\n");
    assertEquals(nodes.size(), 0);
  }

  @Test
  public void readsMultipleUnixNewlinesAsNothing() throws Exception {
    ImmutableList<Node> nodes = readString("\n\n");
    assertEquals(nodes.size(), 0);
  }

  @Test
  public void readsAWindowsNewlineAsNothing() throws Exception {
    ImmutableList<Node> nodes = readString("\r");
    assertEquals(nodes.size(), 0);
  }

  @Test
  public void readsMultipleWindowsNewlinesAsNothing() throws Exception {
    ImmutableList<Node> nodes = readString("\r\r");
    assertEquals(nodes.size(), 0);
  }

  @Test
  public void readsCommaAsNothing() throws Exception {
    ImmutableList<Node> nodes = readString(",");
    assertEquals(nodes.size(), 0);
  }

  @Test
  public void readsMultipleCommasAsNothing() throws Exception {
    ImmutableList<Node> nodes = readString(",,");
    assertEquals(nodes.size(), 0);
  }

  @Test
  public void readsNil() throws Exception {
    Node node = readStringFirstNode("nil");
    assertThat(node, instanceOf(NilNode.class));
  }

  @Test
  public void readsNilPrecededByWhitespace() throws Exception {
    Node node = readStringFirstNode("\nnil");
    assertThat(node, instanceOf(NilNode.class));
  }

  @Test
  public void readsNilFollowedByWhitespace() throws Exception {
    Node node = readStringFirstNode("\nnil\n");
    assertThat(node, instanceOf(NilNode.class));
  }

  @Test
  public void readsTrue() throws Exception {
    Node node = readStringFirstNode("true");
    assertThat(node, instanceOf(BooleanNode.class));
    assertTrue(((BooleanNode) node).getValue());
  }

  @Test
  public void readsTrueFollowedByWhitespace() throws Exception {
    Node node = readStringFirstNode("true\n");
    assertThat(node, instanceOf(BooleanNode.class));
    assertTrue(((BooleanNode) node).getValue());
  }

  @Test
  public void readsFalse() throws Exception {
    Node node = readStringFirstNode("false");
    assertThat(node, instanceOf(BooleanNode.class));
    assertFalse(((BooleanNode) node).getValue());
  }

  @Test
  public void readsFalseFollowedByWhiteSpace() throws Exception {
    Node node = readStringFirstNode("false\n");
    assertThat(node, instanceOf(BooleanNode.class));
    assertFalse(((BooleanNode) node).getValue());
  }

  @Test
  public void readSymbolWithoutNs() throws Exception {
    Node node = readStringFirstNode("a-symbol");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals(node1.getName(), "a-symbol");
    assertEquals(node1.getNamespace(), "");
  }

  @Test
  public void readSymbolPrecededByWhitespace() throws Exception {
    Node node = readStringFirstNode("\na-symbol");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals(node1.getName(), "a-symbol");
    assertEquals(node1.getNamespace(), "");
  }

  @Test
  public void readSymbolFollowedByWhitespace() throws Exception {
    Node node = readStringFirstNode("\na-symbol\n");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals(node1.getName(), "a-symbol");
    assertEquals(node1.getNamespace(), "");
  }

  @Test
  public void readSymbolWithNamespace() throws Exception {
    Node node = readStringFirstNode("ns/a-symbol");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals(node1.getName(), "a-symbol");
    assertEquals(node1.getNamespace(), "ns");
  }

  @Test
  public void readKeyword() throws Exception {
    Node node = readStringFirstNode(":a-keyword");
    assertThat(node, instanceOf(KeywordNode.class));
    KeywordNode node1 = (KeywordNode) node;
    assertEquals(node1.getName(), "a-keyword");
    assertEquals(node1.getNamespace(), "");
  }

  @Test
  public void readKeywordWithNamespace() throws Exception {
    Node node = readStringFirstNode(":ns/a-keyword");
    assertThat(node, instanceOf(KeywordNode.class));
    KeywordNode node1 = (KeywordNode) node;
    assertEquals(node1.getName(), "a-keyword");
    assertEquals(node1.getNamespace(), "ns");
  }

  @Test
  public void readKeywordFollowedByWhitespace() throws Exception {
    Node node = readStringFirstNode(":a-keyword\n");
    assertThat(node, instanceOf(KeywordNode.class));
    KeywordNode node1 = (KeywordNode) node;
    assertEquals(node1.getName(), "a-keyword");
    assertEquals(node1.getNamespace(), "");
  }

  @Test
  public void readSingleDigit() throws Exception {
    for (Integer i = 0; i < 10; i++) {
      String intAsString = i.toString();
      Node node = readStringFirstNode(intAsString);
      assertThat(node, instanceOf(IntegerNode.class));
      IntegerNode node1 = (IntegerNode) node;
      assertEquals(node1.getValueAsString(), intAsString);
      assertFalse(node1.isArbitraryPrecision());
      assertTrue(node1.isPositive());
    }
  }

  @Test
  public void readSingleDigitFollowedByWhitespace() throws Exception {
    for (Integer i = 0; i < 10; i++) {
      String intAsString = i.toString();
      Node node = readStringFirstNode(intAsString + "\n");
      assertThat(node, instanceOf(IntegerNode.class));
      IntegerNode node1 = (IntegerNode) node;
      assertEquals(node1.getValueAsString(), intAsString);
      assertFalse(node1.isArbitraryPrecision());
      assertTrue(node1.isPositive());
    }
  }

  @Test
  public void readSingleDigitArbitraryPosition() throws Exception {
    for (Integer i = 0; i < 10; i++) {
      String intAsString = i.toString();
      Node node = readStringFirstNode(intAsString + "N");
      assertThat(node, instanceOf(IntegerNode.class));
      IntegerNode node1 = (IntegerNode) node;
      assertEquals(node1.getValueAsString(), intAsString);
      assertTrue(node1.isArbitraryPrecision());
      assertTrue(node1.isPositive());
    }
  }

  @Test
  public void readSingleDigitArbitraryPositionFollowedByWhitespace() throws Exception {
    for (Integer i = 0; i < 10; i++) {
      String intAsString = i.toString();
      Node node = readStringFirstNode(intAsString + "N\n");
      assertThat(node, instanceOf(IntegerNode.class));
      IntegerNode node1 = (IntegerNode) node;
      assertEquals(node1.getValueAsString(), intAsString);
      assertTrue(node1.isArbitraryPrecision());
      assertTrue(node1.isPositive());
    }
  }

  @Test
  public void readsMultipleDigits() throws Exception {
    Node node = readStringFirstNode("123");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("123", node1.getValueAsString());
    assertFalse(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsMultipleDigitsArbitraryPosition() throws Exception {
    Node node = readStringFirstNode("123N");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("123", node1.getValueAsString());
    assertTrue(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsMultipleDigitsArbitraryPositionFollowedByWhitespace() throws Exception {
    Node node = readStringFirstNode("123N\n");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("123", node1.getValueAsString());
    assertTrue(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsALargeInteger() throws Exception {
    Node node = readStringFirstNode("12345678900987654321234567890");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("12345678900987654321234567890", node1.getValueAsString());
    assertFalse(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsAnIntegerWithAPlusSignInFront() throws Exception {
    Node node = readStringFirstNode("+1");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("1", node1.getValueAsString());
    assertFalse(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsAnIntegerWithAMinusSignInFront() throws Exception {
    Node node = readStringFirstNode("-1");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("1", node1.getValueAsString());
    assertFalse(node1.isArbitraryPrecision());
    assertFalse(node1.isPositive());
  }

  @Test
  public void readsAnIntegerWithAPlusInFrontAndArbitraryPrecision() throws Exception {
    Node node = readStringFirstNode("+1234567890N");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("1234567890", node1.getValueAsString());
    assertTrue(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsAnIntegerWithAMinusSignInFrontAndArbitraryPrecision() throws Exception {
    Node node = readStringFirstNode("-1234567890N");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("1234567890", node1.getValueAsString());
    assertTrue(node1.isArbitraryPrecision());
    assertFalse(node1.isPositive());
  }

  @Test
  public void readAPlusSymbol() throws Exception {
    Node node = readStringFirstNode("+");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals("+", node1.getName());
    assertEquals("", node1.getNamespace());
  }

  @Test
  public void readAMinusSymbol() throws Exception {
    Node node = readStringFirstNode("-");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals("-", node1.getName());
    assertEquals("", node1.getNamespace());
  }
}
