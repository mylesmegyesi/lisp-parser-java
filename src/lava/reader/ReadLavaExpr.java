package lava.reader;

import lava.util.ImmutableArrayList;

public class ReadLavaExpr implements ReadState {

  private ExprReadStateFactory exprReadStateFactory;
  private ParentReadState parentReadState;

  public ReadLavaExpr(ExprReadStateFactory exprReadStateFactory, ParentReadState parentReadState) {
    this.exprReadStateFactory = exprReadStateFactory;
    this.parentReadState = parentReadState;
  }

  public ReadResult handle(char c) {
    if (Util.isWhitespace(c)) {
      return ReadResultFactory.notDoneYet(new ReadLavaExpr(this.exprReadStateFactory, this.parentReadState));
    } else if (this.parentReadState.terminal(c)) {
      return this.finish();
    } else if (Character.isDigit(c)) {
      return new ReadInteger(this.parentReadState, true, false).handle(c);
    } else if (c == ':') {
      return ReadResultFactory.notDoneYet(new ReadKeyword(this.parentReadState));
    } else if (c == '+') {
      return ReadResultFactory.notDoneYet(new ReadInteger(this.parentReadState, true, true));
    } else if (c == '-') {
      return ReadResultFactory.notDoneYet(new ReadInteger(this.parentReadState, false, true));
    } else if (c == '(') {
      return ReadResultFactory.notDoneYet(new ReadList(this.exprReadStateFactory, this.parentReadState));

    } else {
      return new ReadSymbol(this.parentReadState).handle(c);
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(new ImmutableArrayList<AstNode>());
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }

}
