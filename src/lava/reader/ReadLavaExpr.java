package lava.reader;

import com.google.common.collect.ImmutableList;

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
      return ReadResultFactory.notDoneYet(new ReadCollection(this.exprReadStateFactory, this.parentReadState, new ListReadCollectionStrategy()));
    } else if (c == '[') {
      return ReadResultFactory.notDoneYet(new ReadCollection(this.exprReadStateFactory, this.parentReadState, new VectorReadCollectionStrategy()));
    } else if (c == '#') {
      return ReadResultFactory.notDoneYet(new ReadSawPound(this.exprReadStateFactory, this.parentReadState));
    } else if (c == '"') {
      return ReadResultFactory.notDoneYet(new ReadString(this.parentReadState));
    } else {
      return new ReadSymbol(this.parentReadState).handle(c);
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(ImmutableList.<AstNode>of());
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }

}
