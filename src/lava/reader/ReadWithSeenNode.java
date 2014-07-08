package lava.reader;

import lava.util.ImmutableList;

public class ReadWithSeenNode implements ReadState {

  private ReadState wrappedState;
  private ImmutableList<Node> seenNodes;

  public ReadWithSeenNode(ImmutableList<Node> seenNodes, ReadState readState) {
    this.seenNodes = seenNodes;
    this.wrappedState = readState;
  }

  public ReadResult handle(char c) {
    ReadResult result = this.wrappedState.handle(c);
    if (result.isFinished()) {
      if (result.isSuccess()) {
        return ReadResultFactory.done(this.seenNodes.append(result.getNodes()));
      } else {
        return result;
      }
    } else {
      return ReadResultFactory.notDoneYet(new ReadWithSeenNode(this.seenNodes, result.getNextState()));
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(this.seenNodes);
  }
}
