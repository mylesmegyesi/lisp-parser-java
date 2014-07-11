package lava.reader;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoneReadResultTest {

  private ImmutableList<AstNode> testNodes = ImmutableList.<AstNode>of(SymbolNode.fromString("abc"));
  private DoneReadResult testResult = new DoneReadResult(testNodes);

  @Test
  public void isAlwaysFinished() {
    assertTrue(testResult.isFinished());
  }

  @Test
  public void isAlwaysSuccessful() {
    assertTrue(testResult.isSuccess());
  }

  @Test
  public void nextStateIsNull() {
    assertNull(testResult.getNextState());
  }

  @Test
  public void getNodesReturnsThePassedInNodes() {
    assertEquals(testNodes, testResult.getNodes());
  }

  @Test
  public void getReadErrorReturnsNull() {
    assertNull(testResult.getReadError());
  }
}
