package lava.reader;

import lava.util.ImmutableList;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class LavaReaderTest {

  private ImmutableList<AstNode> readString(String s) {
    LavaReader reader = new LavaReader();
    ReadResult result = reader.readString(s);
    return result.getNodes();
  }

  private AstNode readStringFirstNode(String s) throws Exception {
    ImmutableList<AstNode> nodes = readString(s);
    if (nodes.size() == 1) {
      return nodes.first();
    } else if (nodes.size() == 0) {
      fail("Tried read first node of string but no nodes: " + nodes.toString());
    } else {
      fail("Tried read first node of string but multiple nodes read: " + nodes.toString());
    }
    return null;
  }

  @Test
  public void readsAnEmptyStringAsNothing() throws Exception {
    ImmutableList<AstNode> nodes = readString("");
    assertEquals(0, nodes.size());
  }

  @Test
  public void readsASpaceAsNothing() throws Exception {
    ImmutableList<AstNode> nodes = readString(" ");
    assertEquals(0, nodes.size());
  }

  @Test
  public void readsMultipleSpacesAsNothing() throws Exception {
    ImmutableList<AstNode> nodes = readString("  ");
    assertEquals(0, nodes.size());
  }

  @Test
  public void readsAUnixNewlineAsNothing() throws Exception {
    ImmutableList<AstNode> nodes = readString("\n");
    assertEquals(0, nodes.size());
  }

  @Test
  public void readsMultipleUnixNewlinesAsNothing() throws Exception {
    ImmutableList<AstNode> nodes = readString("\n\n");
    assertEquals(0, nodes.size());
  }

  @Test
  public void readsAWindowsNewlineAsNothing() throws Exception {
    ImmutableList<AstNode> nodes = readString("\r");
    assertEquals(0, nodes.size());
  }

  @Test
  public void readsMultipleWindowsNewlinesAsNothing() throws Exception {
    ImmutableList<AstNode> nodes = readString("\r\r");
    assertEquals(0, nodes.size());
  }

  @Test
  public void readsCommaAsNothing() throws Exception {
    ImmutableList<AstNode> nodes = readString(",");
    assertEquals(0, nodes.size());
  }

  @Test
  public void readsMultipleCommasAsNothing() throws Exception {
    ImmutableList<AstNode> nodes = readString(",,");
    assertEquals(0, nodes.size());
  }

  @Test
  public void readsNil() throws Exception {
    AstNode node = readStringFirstNode("nil");
    assertThat(node, instanceOf(NilNode.class));
  }

  @Test
  public void readsNilPrecededByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("\nnil");
    assertThat(node, instanceOf(NilNode.class));
  }

  @Test
  public void readsNilFollowedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("\nnil\n");
    assertThat(node, instanceOf(NilNode.class));
  }

  @Test
  public void readsTrue() throws Exception {
    AstNode node = readStringFirstNode("true");
    assertThat(node, instanceOf(BooleanNode.class));
    assertTrue(((BooleanNode) node).getValue());
  }

  @Test
  public void readsTrueFollowedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("true\n");
    assertThat(node, instanceOf(BooleanNode.class));
    assertTrue(((BooleanNode) node).getValue());
  }

  @Test
  public void readsFalse() throws Exception {
    AstNode node = readStringFirstNode("false");
    assertThat(node, instanceOf(BooleanNode.class));
    assertFalse(((BooleanNode) node).getValue());
  }

  @Test
  public void readsFalseFollowedByWhiteSpace() throws Exception {
    AstNode node = readStringFirstNode("false\n");
    assertThat(node, instanceOf(BooleanNode.class));
    assertFalse(((BooleanNode) node).getValue());
  }

  @Test
  public void readSymbolWithoutNs() throws Exception {
    AstNode node = readStringFirstNode("a-symbol");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals("a-symbol", node1.getName());
    assertEquals("", node1.getNamespace());
  }

  @Test
  public void readSymbolPrecededByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("\na-symbol");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals("a-symbol", node1.getName());
    assertEquals("", node1.getNamespace());
  }

  @Test
  public void readSymbolFollowedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("\na-symbol\n");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals("a-symbol", node1.getName());
    assertEquals("", node1.getNamespace());
  }

  @Test
  public void readSymbolWithNamespace() throws Exception {
    AstNode node = readStringFirstNode("ns/a-symbol");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals("a-symbol", node1.getName());
    assertEquals("ns", node1.getNamespace());
  }

  @Test
  public void readKeyword() throws Exception {
    AstNode node = readStringFirstNode(":a-keyword");
    assertThat(node, instanceOf(KeywordNode.class));
    KeywordNode node1 = (KeywordNode) node;
    assertEquals("a-keyword", node1.getName());
    assertEquals("", node1.getNamespace());
  }

  @Test
  public void readKeywordWithNamespace() throws Exception {
    AstNode node = readStringFirstNode(":ns/a-keyword");
    assertThat(node, instanceOf(KeywordNode.class));
    KeywordNode node1 = (KeywordNode) node;
    assertEquals("a-keyword", node1.getName());
    assertEquals("ns", node1.getNamespace());
  }

  @Test
  public void readKeywordFollowedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode(":a-keyword\n");
    assertThat(node, instanceOf(KeywordNode.class));
    KeywordNode node1 = (KeywordNode) node;
    assertEquals("a-keyword", node1.getName());
    assertEquals("", node1.getNamespace());
  }

  @Test
  public void readSingleDigit() throws Exception {
    for (Integer i = 0; i < 10; i++) {
      String intAsString = i.toString();
      AstNode node = readStringFirstNode(intAsString);
      assertThat(node, instanceOf(IntegerNode.class));
      IntegerNode node1 = (IntegerNode) node;
      assertEquals(intAsString, node1.getValueAsString());
      assertFalse(node1.isArbitraryPrecision());
      assertTrue(node1.isPositive());
    }
  }

  @Test
  public void readSingleDigitFollowedByWhitespace() throws Exception {
    for (Integer i = 0; i < 10; i++) {
      String intAsString = i.toString();
      AstNode node = readStringFirstNode(intAsString + "\n");
      assertThat(node, instanceOf(IntegerNode.class));
      IntegerNode node1 = (IntegerNode) node;
      assertEquals(intAsString, node1.getValueAsString());
      assertFalse(node1.isArbitraryPrecision());
      assertTrue(node1.isPositive());
    }
  }

  @Test
  public void readSingleDigitArbitraryPosition() throws Exception {
    for (Integer i = 0; i < 10; i++) {
      String intAsString = i.toString();
      AstNode node = readStringFirstNode(intAsString + "N");
      assertThat(node, instanceOf(IntegerNode.class));
      IntegerNode node1 = (IntegerNode) node;
      assertEquals(intAsString, node1.getValueAsString());
      assertTrue(node1.isArbitraryPrecision());
      assertTrue(node1.isPositive());
    }
  }

  @Test
  public void readSingleDigitArbitraryPositionFollowedByWhitespace() throws Exception {
    for (Integer i = 0; i < 10; i++) {
      String intAsString = i.toString();
      AstNode node = readStringFirstNode(intAsString + "N\n");
      assertThat(node, instanceOf(IntegerNode.class));
      IntegerNode node1 = (IntegerNode) node;
      assertEquals(intAsString, node1.getValueAsString());
      assertTrue(node1.isArbitraryPrecision());
      assertTrue(node1.isPositive());
    }
  }

  @Test
  public void readsMultipleDigits() throws Exception {
    AstNode node = readStringFirstNode("123");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("123", node1.getValueAsString());
    assertFalse(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsMultipleDigitsArbitraryPosition() throws Exception {
    AstNode node = readStringFirstNode("123N");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("123", node1.getValueAsString());
    assertTrue(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsMultipleDigitsArbitraryPositionFollowedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("123N\n");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("123", node1.getValueAsString());
    assertTrue(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsALargeInteger() throws Exception {
    AstNode node = readStringFirstNode("12345678900987654321234567890");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("12345678900987654321234567890", node1.getValueAsString());
    assertFalse(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsAnIntegerWithAPlusSignInFront() throws Exception {
    AstNode node = readStringFirstNode("+1");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("1", node1.getValueAsString());
    assertFalse(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsAnIntegerWithAMinusSignInFront() throws Exception {
    AstNode node = readStringFirstNode("-1");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("1", node1.getValueAsString());
    assertFalse(node1.isArbitraryPrecision());
    assertFalse(node1.isPositive());
  }

  @Test
  public void readsAnIntegerWithAPlusInFrontAndArbitraryPrecision() throws Exception {
    AstNode node = readStringFirstNode("+1234567890N");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("1234567890", node1.getValueAsString());
    assertTrue(node1.isArbitraryPrecision());
    assertTrue(node1.isPositive());
  }

  @Test
  public void readsAnIntegerWithAMinusSignInFrontAndArbitraryPrecision() throws Exception {
    AstNode node = readStringFirstNode("-1234567890N");
    assertThat(node, instanceOf(IntegerNode.class));
    IntegerNode node1 = (IntegerNode) node;
    assertEquals("1234567890", node1.getValueAsString());
    assertTrue(node1.isArbitraryPrecision());
    assertFalse(node1.isPositive());
  }

  @Test
  public void readAPlusSymbol() throws Exception {
    AstNode node = readStringFirstNode("+");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals("+", node1.getName());
    assertEquals("", node1.getNamespace());
  }

  @Test
  public void readAMinusSymbol() throws Exception {
    AstNode node = readStringFirstNode("-");
    assertThat(node, instanceOf(SymbolNode.class));
    SymbolNode node1 = (SymbolNode) node;
    assertEquals("-", node1.getName());
    assertEquals("", node1.getNamespace());
  }

  @Test
  public void readAnIntWithAnM() throws Exception {
    AstNode node = readStringFirstNode("123M");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("", node1.getDecimalValueAsString());
    assertEquals("", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAnIntWithAnMAndFollowedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("123M\n");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("", node1.getDecimalValueAsString());
    assertEquals("", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readANegativeIntWithAnM() throws Exception {
    AstNode node = readStringFirstNode("-123M");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("", node1.getDecimalValueAsString());
    assertEquals("", node1.getExponentValueAsString());
    assertFalse(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAPositiveFloatWithADecimal() throws Exception {
    AstNode node = readStringFirstNode("123.456");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAPositiveFloatWithADecimalFollowedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("123.456\n");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAPositiveFloatWithADecimalWithAPlusSign() throws Exception {
    AstNode node = readStringFirstNode("+123.456");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAPositiveFloatWithADecimalWithAMinusSign() throws Exception {
    AstNode node = readStringFirstNode("-123.456");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("", node1.getExponentValueAsString());
    assertFalse(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAFloatWithALowercaseEExponent() throws Exception {
    AstNode node = readStringFirstNode("123.456e789");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("789", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAFloatWithALowercaseEExponentWithPlusSign() throws Exception {
    AstNode node = readStringFirstNode("123.456e+789");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("789", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAFloatWithALowercaseEExponentWithMinusSign() throws Exception {
    AstNode node = readStringFirstNode("123.456e-789");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("789", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertFalse(node1.isExponentPositive());
  }

  @Test
  public void readAFloatWithAnUppercaseEExp() throws Exception {
    AstNode node = readStringFirstNode("123.456E789");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("789", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAFloatWithAnUppercaseEExponentWithPlusSign() throws Exception {
    AstNode node = readStringFirstNode("123.456E+789");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("789", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAFloatWithAnUppercaseEExponentWithMinusSign() throws Exception {
    AstNode node = readStringFirstNode("123.456E-789");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("789", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertFalse(node1.isExponentPositive());
  }

  // TODO
  // 123.456e\n - newline immediately following e
  // 123.456e+\n - newline immediately following plus sign
  // 123.456e+\n - newline immediately following minus sign
  // 123.456e7+89 - misplaced sign
  // 123.456e789a - invalid character

  @Test
  public void readAFloatWithExponentFollowedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("123.456e789\n");
    assertThat(node, instanceOf(FloatNode.class));
    FloatNode node1 = (FloatNode) node;
    assertEquals("123", node1.getIntValueAsString());
    assertEquals("456", node1.getDecimalValueAsString());
    assertEquals("789", node1.getExponentValueAsString());
    assertTrue(node1.isPositive());
    assertTrue(node1.isExponentPositive());
  }

  @Test
  public void readAnEmptyList() throws Exception {
    AstNode node = readStringFirstNode("()");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(0, listNodes.size());
  }

  @Test
  public void readAnEmptyListWithASpace() throws Exception {
    AstNode node = readStringFirstNode("( )");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(0, listNodes.size());
  }

  @Test
  public void readAnEmptyListWithMultipleSpaces() throws Exception {
    AstNode node = readStringFirstNode("(  )");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(0, listNodes.size());
  }

  @Test
  public void readAnEmptyListWithANewline() throws Exception {
    AstNode node = readStringFirstNode("(\n)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(0, listNodes.size());
  }

  @Test
  public void readAListWithOneSymbol() throws Exception {
    AstNode node = readStringFirstNode("(a)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    SymbolNode symbolNode = (SymbolNode) listNodes.first();
    assertEquals("a", symbolNode.getName());
    assertEquals("", symbolNode.getNamespace());
  }

  @Test
  public void readAListWithOneSymbolPrecededByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("(\na)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    SymbolNode symbolNode = (SymbolNode) listNodes.first();
    assertEquals("a", symbolNode.getName());
    assertEquals("", symbolNode.getNamespace());
  }

  @Test
  public void readAListWithOneSymbolFollowedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("(\na\n)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    SymbolNode symbolNode = (SymbolNode) listNodes.first();
    assertEquals("a", symbolNode.getName());
    assertEquals("", symbolNode.getNamespace());
  }

  @Test
  public void readAListWithTwoSymbolsSeparatedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("(a b)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(2, listNodes.size());

    SymbolNode symbolNode1 = (SymbolNode) listNodes.first();
    assertEquals("a", symbolNode1.getName());
    assertEquals("", symbolNode1.getNamespace());

    SymbolNode symbolNode2 = (SymbolNode) listNodes.get(1);
    assertEquals("b", symbolNode2.getName());
    assertEquals("", symbolNode2.getNamespace());
  }

  @Test
  public void readAListWithFiveSymbolsSeparatedByWhitespace() throws Exception {
    AstNode node = readStringFirstNode("(\n a, ,b, the-ns/c\n)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(3, listNodes.size());

    SymbolNode symbolNode1 = (SymbolNode) listNodes.first();
    assertEquals("a", symbolNode1.getName());
    assertEquals("", symbolNode1.getNamespace());

    SymbolNode symbolNode2 = (SymbolNode) listNodes.get(1);
    assertEquals("b", symbolNode2.getName());
    assertEquals("", symbolNode2.getNamespace());

    SymbolNode symbolNode3 = (SymbolNode) listNodes.get(2);
    assertEquals("c", symbolNode3.getName());
    assertEquals("the-ns", symbolNode3.getNamespace());
  }

  @Test
  public void readsMultipleExpressions() throws Exception {
    ImmutableList<AstNode> nodes = readString("abc def");
    assertEquals(2, nodes.size());

    SymbolNode symbolNode1 = (SymbolNode) nodes.first();
    assertEquals("abc", symbolNode1.getName());
    assertEquals("", symbolNode1.getNamespace());

    SymbolNode symbolNode2 = (SymbolNode) nodes.get(1);
    assertEquals("def", symbolNode2.getName());
    assertEquals("", symbolNode2.getNamespace());
  }

  @Test
  public void readsAListExpressionThenAnotherExpression() throws Exception {
    ImmutableList<AstNode> nodes = readString("(a b) def");
    assertEquals(2, nodes.size());

    ImmutableList<AstNode> listNodes = ((ListNode) nodes.first()).getNodes();
    assertEquals(2, listNodes.size());

    SymbolNode symbolNode1 = (SymbolNode) listNodes.first();
    assertEquals("a", symbolNode1.getName());
    assertEquals("", symbolNode1.getNamespace());

    SymbolNode symbolNode2 = (SymbolNode) listNodes.get(1);
    assertEquals("b", symbolNode2.getName());
    assertEquals("", symbolNode2.getNamespace());

    SymbolNode symbolNode3 = (SymbolNode) nodes.get(1);
    assertEquals("def", symbolNode3.getName());
    assertEquals("", symbolNode3.getNamespace());
  }

  @Test
  public void readAListWithOneInteger() throws Exception {
    AstNode node = readStringFirstNode("(1)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    IntegerNode integerNode = (IntegerNode) listNodes.first();
    assertEquals("1", integerNode.getValueAsString());
    assertTrue(integerNode.isPositive());
    assertFalse(integerNode.isArbitraryPrecision());
  }

  @Test
  public void readAListWithTwoIntegers() throws Exception {
    AstNode node = readStringFirstNode("(1 -2)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(2, listNodes.size());

    IntegerNode integerNode1 = (IntegerNode) listNodes.first();
    assertEquals("1", integerNode1.getValueAsString());
    assertTrue(integerNode1.isPositive());
    assertFalse(integerNode1.isArbitraryPrecision());

    IntegerNode integerNode2 = (IntegerNode) listNodes.get(1);
    assertEquals("2", integerNode2.getValueAsString());
    assertFalse(integerNode2.isPositive());
    assertFalse(integerNode2.isArbitraryPrecision());
  }

  @Test
  public void readAListWithOneFloat() throws Exception {
    AstNode node = readStringFirstNode("(1.0)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    FloatNode floatNode = (FloatNode) listNodes.first();
    assertEquals("1", floatNode.getIntValueAsString());
    assertEquals("0", floatNode.getDecimalValueAsString());
  }

  @Test
  public void readAListWithOneFloatWithExponent() throws Exception {
    AstNode node = readStringFirstNode("(1.0E1)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    FloatNode floatNode = (FloatNode) listNodes.first();
    assertEquals("1", floatNode.getIntValueAsString());
    assertEquals("0", floatNode.getDecimalValueAsString());
    assertEquals("1", floatNode.getExponentValueAsString());
  }

  @Test
  public void readAListWithOneKeyword() throws Exception {
    AstNode node = readStringFirstNode("(:a)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    KeywordNode keywordNode = (KeywordNode) listNodes.first();
    assertEquals("a", keywordNode.getName());
    assertEquals("", keywordNode.getNamespace());
  }

  @Test
  public void readAListWithNil() throws Exception {
    AstNode node = readStringFirstNode("(nil)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    assertThat(listNodes.first(), instanceOf(NilNode.class));
  }

  @Test
  public void readAListWithTrue() throws Exception {
    AstNode node = readStringFirstNode("(true)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    BooleanNode booleanNode = (BooleanNode) listNodes.first();
    assertTrue(booleanNode.getValue());
  }

  @Test
  public void readAListWithFalse() throws Exception {
    AstNode node = readStringFirstNode("(false)");
    assertThat(node, instanceOf(ListNode.class));
    ListNode node1 = (ListNode) node;
    ImmutableList<AstNode> listNodes = node1.getNodes();
    assertEquals(1, listNodes.size());
    BooleanNode booleanNode = (BooleanNode) listNodes.first();
    assertFalse(booleanNode.getValue());
  }
}
