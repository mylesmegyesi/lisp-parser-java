package lava.reader;

import com.google.common.collect.ImmutableList;

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
    return ImmutableList.<AstNode>of();
  }

  public ReadError getReadError() {
    return null;
  }
}
