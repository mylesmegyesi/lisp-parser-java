package lava.reader;

public class ReadKeyword implements ReadState {

  private SeenChars seenChars;

  ReadKeyword() {
    this.seenChars = new SeenChars();
  }

  ReadKeyword(SeenChars seenChars) {
    this.seenChars = seenChars;
  }

  public ReadResult handle(char c) {
    if (Util.isWhitespace(c)) {
      return this.finish();
    } else {
      return ReadResultFactory.notDoneYet(new ReadKeyword(this.seenChars.add(c)));
    }
  }

  public ReadResult finish() {
    SymbolNode node = SymbolNode.fromString(this.seenChars.toString());
    return ReadResultFactory.done(new KeywordNode(node));
  }
}
