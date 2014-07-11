package lava.reader;

import com.google.common.collect.ImmutableList;

public class ReadCollection implements ReadState, ParentReadState {

  private ExprReadStateFactory exprReadStateFactory;
  private ReadState wrappedExprState;
  private ParentReadState parentReadState;
  private ImmutableList<AstNode> seenNodes;
  private ReadCollectionStrategy strategy;

  public ReadCollection(ExprReadStateFactory exprReadStateFactory, ParentReadState parentReadState, ReadCollectionStrategy strategy) {
    this.exprReadStateFactory = exprReadStateFactory;
    this.wrappedExprState = exprReadStateFactory.newExprReadState(this);
    this.parentReadState = parentReadState;
    this.seenNodes = ImmutableList.<AstNode>of();
    this.strategy = strategy;
  }

  public ReadCollection(ExprReadStateFactory exprReadStateFactory, ParentReadState parentReadState, ReadState wrappedExprState, ReadCollectionStrategy strategy, ImmutableList<AstNode> seenNodes) {
    this.exprReadStateFactory = exprReadStateFactory;
    this.wrappedExprState = wrappedExprState;
    this.parentReadState = parentReadState;
    this.strategy = strategy;
    this.seenNodes = seenNodes;
  }

  public ReadResult handle(char c) {
    ReadResult wrappedResult = this.wrappedExprState.handle(c);
    if (wrappedResult.isFinished()) {
      if (wrappedResult.isSuccess()) {
        ImmutableList.Builder<AstNode> builder = ImmutableList.<AstNode>builder();
        ImmutableList<AstNode> allNodes = builder.addAll(this.seenNodes).addAll(wrappedResult.getNodes()).build();
        if (this.terminal(c)) {
          return ReadResultFactory.done(this.strategy.createNode(allNodes));
        } else {
          return ReadResultFactory.notDoneYet(new ReadCollection(this.exprReadStateFactory, this.parentReadState, exprReadStateFactory.newExprReadState(this), this.strategy, allNodes));
        }
      } else {
        return wrappedResult;
      }
    } else {
      return ReadResultFactory.notDoneYet(new ReadCollection(this.exprReadStateFactory, this.parentReadState, wrappedResult.getNextState(), this.strategy, this.seenNodes));
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(this.strategy.createNode(this.seenNodes));
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }

  public boolean terminal(char c) {
    return c == this.strategy.getTerminalChar();
  }
}

