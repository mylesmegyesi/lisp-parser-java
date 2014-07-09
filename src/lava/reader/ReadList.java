package lava.reader;

import lava.util.ImmutableArrayList;
import lava.util.ImmutableList;

public class ReadList implements ReadState, ParentReadState {

  private ExprReadStateFactory exprReadStateFactory;
  private ReadState wrappedExprState;
  private ParentReadState parentReadState;
  private ImmutableList<AstNode> seenNodes;

  public ReadList(ExprReadStateFactory exprReadStateFactory, ParentReadState parentReadState) {
    this.exprReadStateFactory = exprReadStateFactory;
    this.wrappedExprState = exprReadStateFactory.newExprReadState(this);
    this.parentReadState = parentReadState;
    this.seenNodes = new ImmutableArrayList<AstNode>();
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
        ImmutableList<AstNode> allNodes = this.seenNodes.append(wrappedResult.getNodes());
        for (AstNode node : allNodes) {
        }
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
