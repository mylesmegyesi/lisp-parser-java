package lava.reader;

import com.google.common.collect.ImmutableList;

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
        return ReadResultFactory.done(this.seenNodesWith(result.getNodes()));
      } else {
        return result;
      }
    } else {
      return ReadResultFactory.notDoneYet(new ReadWithSeenNode(this.parentReadState, this.seenNodes, result.getNextState()));
    }
  }

  public ReadResult finish() {
    ReadResult result = this.wrappedState.finish();
    if (result.isFinished()) {
      if (result.isSuccess()) {
        return ReadResultFactory.done(this.seenNodesWith(result.getNodes()));
      } else {
        return result;
      }
    } else {
      return ReadResultFactory.notDoneYet(new ReadWithSeenNode(this.parentReadState, this.seenNodes, result.getNextState()));
    }
  }

  public boolean terminal(char c) {
    return false;
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }

  private ImmutableList<AstNode> seenNodesWith(ImmutableList<AstNode> nodes) {
    ImmutableList.Builder<AstNode> builder = ImmutableList.<AstNode>builder();
    return builder.addAll(this.seenNodes).addAll(nodes).build();
  }
}
