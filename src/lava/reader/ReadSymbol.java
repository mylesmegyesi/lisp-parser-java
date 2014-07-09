package lava.reader;

public class ReadSymbol implements ReadState {

  private ParentReadState parentReadState;
  private SeenChars seenChars;

  ReadSymbol(ParentReadState parentReadState) {
    this(parentReadState, new SeenChars());
  }

  ReadSymbol(ParentReadState parentReadState, SeenChars seenChars) {
    this.parentReadState = parentReadState;
    this.seenChars = seenChars;
  }

  public ReadResult handle(char c) {
    SeenChars withNext = this.seenChars.add(c);
    String seenString = withNext.toString();
    if (Util.isWhitespace(c) || this.parentReadState.terminal(c)) {
      return this.finish();
    } else if (seenString.compareTo("nil") == 0) {
      return ReadResultFactory.done(new NilNode());
    } else if (seenString.compareTo("true") == 0) {
      return ReadResultFactory.done(new BooleanNode(true));
    } else if (seenString.compareTo("false") == 0) {
      return ReadResultFactory.done(new BooleanNode(false));
    } else {
      return ReadResultFactory.notDoneYet(new ReadSymbol(this.parentReadState, withNext));
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(SymbolNode.fromString(this.seenChars.toString()));
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }
}
