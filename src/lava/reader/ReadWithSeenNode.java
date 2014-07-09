package lava.reader;

import lava.util.ImmutableList;

public class ReadWithSeenNode implements ReadState {

  private ReadState wrappedState;
  private ImmutableList<AstNode> seenNodes;
  private ParentReadState parentReadState;

  public ReadWithSeenNode(ParentReadState parentReadState, ImmutableList<AstNode> seenNodes, ReadState readState) {
    this.parentReadState = parentReadState;
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
      return ReadResultFactory.notDoneYet(new ReadWithSeenNode(this.parentReadState, this.seenNodes, result.getNextState()));
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(this.seenNodes);
  }

  public boolean terminal(char c) {
    return false;
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }
}
