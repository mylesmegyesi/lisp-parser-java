package lava.reader;

import com.google.common.collect.ImmutableList;

public class ReadList implements ReadState, ParentReadState {

  private ExprReadStateFactory exprReadStateFactory;
  private ReadState wrappedExprState;
  private ParentReadState parentReadState;
  private ImmutableList<AstNode> seenNodes;

  public ReadList(ExprReadStateFactory exprReadStateFactory, ParentReadState parentReadState) {
    this.exprReadStateFactory = exprReadStateFactory;
    this.wrappedExprState = exprReadStateFactory.newExprReadState(this);
    this.parentReadState = parentReadState;
    this.seenNodes = ImmutableList.<AstNode>of();
  }

  public ReadList(ExprReadStateFactory exprReadStateFactory, ParentReadState parentReadState, ReadState wrappedExprState, ImmutableList<AstNode> seenNodes) {
    this.exprReadStateFactory = exprReadStateFactory;
    this.wrappedExprState = wrappedExprState;
    this.parentReadState = parentReadState;
    this.seenNodes = seenNodes;
  }

  public ReadResult handle(char c) {
    ReadResult wrappedResult = this.wrappedExprState.handle(c);
    if (wrappedResult.isFinished()) {
      if (wrappedResult.isSuccess()) {
        ImmutableList.Builder<AstNode> builder = ImmutableList.<AstNode>builder();
        ImmutableList<AstNode> allNodes = builder.addAll(this.seenNodes).addAll(wrappedResult.getNodes()).build();
        if (this.terminal(c)) {
          return ReadResultFactory.done(new ListNode(allNodes));
        } else {
          return ReadResultFactory.notDoneYet(new ReadList(this.exprReadStateFactory, this.parentReadState, exprReadStateFactory.newExprReadState(this), allNodes));
        }
      } else {
        return wrappedResult;
      }
    } else {
      return ReadResultFactory.notDoneYet(new ReadList(this.exprReadStateFactory, this.parentReadState, wrappedResult.getNextState(), this.seenNodes));
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(new ListNode(this.seenNodes));
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }

  public boolean terminal(char c) {
    return c == ')';
  }
}
