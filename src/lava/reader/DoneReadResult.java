package lava.reader;

import lava.util.ImmutableList;

public class DoneReadResult implements ReadResult {

  private ImmutableList<Node> resultNodes;

  DoneReadResult(ImmutableList<Node> resultNodes) {
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

  public ImmutableList<Node> getNodes() {
    return this.resultNodes;
  }

  public ReadError getReadError() {
    return null;
  }
}
