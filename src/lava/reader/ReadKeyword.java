package lava.reader;

public class ReadKeyword implements ReadState {

  private ParentReadState parentReadState;
  private SeenChars seenChars;

  ReadKeyword(ParentReadState parentReadState) {
    this(parentReadState, new SeenChars());
  }

  ReadKeyword(ParentReadState parentReadState, SeenChars seenChars) {
    this.parentReadState = parentReadState;
    this.seenChars = seenChars;
  }

  public ReadResult handle(char c) {
    if (Util.isWhitespace(c) || this.parentReadState.terminal(c)) {
      return this.finish();
    } else {
      return ReadResultFactory.notDoneYet(new ReadKeyword(this.parentReadState, this.seenChars.add(c)));
    }
  }

  public ReadResult finish() {
    SymbolNode node = SymbolNode.fromString(this.seenChars.toString());
    return ReadResultFactory.done(new KeywordNode(node));
  }

  @Override
  public ParentReadState getParentReadState() {
    return null;
  }
}
