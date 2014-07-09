package lava.reader;

public class ReadSawPound implements ReadState {

  private ExprReadStateFactory exprReadStateFactory;
  private ParentReadState parentReadState;

  public ReadSawPound(ExprReadStateFactory exprReadStateFactory, ParentReadState parentReadState) {
    this.exprReadStateFactory = exprReadStateFactory;
    this.parentReadState = parentReadState;
  }

  public ReadResult handle(char c) {
    if (c == '{') {
      return ReadResultFactory.notDoneYet(new ReadCollection(this.exprReadStateFactory, this.parentReadState, new SetReadCollectionStrategy()));
    } else {
      // tags
      return null;
    }
  }

  public ReadResult finish() {
    return null;
  }

  public ParentReadState getParentReadState() {
    return null;
  }
}
