package lava.reader;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotDoneReadResultTest {

  private ReadState nextState = new ReadState() {
    public ReadResult handle(char c) {
      return null;
    }

    public ReadResult finish() {
      return null;
    }

    public ParentReadState getParentReadState() {
      return null;
    }
  };
  private NotDoneReadResult testResult = new NotDoneReadResult(nextState);

  @Test
  public void isNotFinished() {
    assertFalse(testResult.isFinished());
  }

  @Test
  public void isAlwaysSuccessful() {
    assertTrue(testResult.isSuccess());
  }

  @Test
  public void nextStateReturnsThePassedInState() {
    assertEquals(nextState, testResult.getNextState());
  }

  @Test
  public void getNodesReturnsAnEmptyList() {
    assertEquals(ImmutableList.<AstNode>of(), testResult.getNodes());
  }

  @Test
  public void getReadErrorReturnsNull() {
    assertNull(testResult.getReadError());
  }
}

