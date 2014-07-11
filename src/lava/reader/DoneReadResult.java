package lava.reader;

import com.google.common.collect.ImmutableList;

public class DoneReadResult implements ReadResult {

  private ImmutableList<AstNode> resultNodes;

  DoneReadResult(ImmutableList<AstNode> resultNodes) {
    this.resultNodes = resultNodes;
  }

  public boolean isSuccess() {
    return true;
  }

  public boolean isFinished() {
    return true;
  }

  public ReadState getNextState() {
    return null;
  }

  public ImmutableList<AstNode> getNodes() {
    return this.resultNodes;
  }

  public ReadError getReadError() {
    return null;
  }
}
