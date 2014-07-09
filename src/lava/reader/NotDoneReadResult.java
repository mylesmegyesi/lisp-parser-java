package lava.reader;

import lava.util.ImmutableArrayList;
import lava.util.ImmutableList;

public class NotDoneReadResult implements ReadResult {

  private ReadState nextState;

  public NotDoneReadResult(ReadState nextState) {
    this.nextState = nextState;
  }

  public boolean isSuccess() {
    return true;
  }

  public boolean isFinished() {
    return false;
  }

  public ReadState getNextState() {
    return this.nextState;
  }

  public ImmutableList<AstNode> getNodes() {
    return new ImmutableArrayList<AstNode>();
  }

  public ReadError getReadError() {
    return null;
  }
}
