package lava.reader;

public class ReadSymbol implements ReadState {

  private SeenChars seenChars;

  ReadSymbol() {
    this.seenChars = new SeenChars();
  }

  ReadSymbol(SeenChars seenChars) {
    this.seenChars = seenChars;
  }

  public ReadResult handle(char c) {
    SeenChars withNext = this.seenChars.add(c);
    String seenString = withNext.toString();
    if (Util.isWhitespace(c)) {
      return this.finish();
    } else if (seenString.compareTo("nil") == 0) {
      return ReadResultFactory.done(new NilNode());
    } else if (seenString.compareTo("true") == 0) {
      return ReadResultFactory.done(new BooleanNode(true));
    } else if (seenString.compareTo("false") == 0) {
      return ReadResultFactory.done(new BooleanNode(false));
    } else {
      return ReadResultFactory.notDoneYet(new ReadSymbol(withNext));
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(SymbolNode.fromString(this.seenChars.toString()));
  }
}
