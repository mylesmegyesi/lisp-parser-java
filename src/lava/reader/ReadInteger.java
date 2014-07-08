package lava.reader;

public class ReadInteger implements ReadState {

  private boolean positive;
  private boolean leadingSign;
  private boolean arbitraryPrecision;
  private SeenChars seenChars;

  public ReadInteger(boolean positive, boolean leadingSign) {
    this(positive, leadingSign, false, new SeenChars());
  }

  public ReadInteger(boolean positive, boolean leadingSign, boolean arbitraryPrecision, SeenChars seenChars) {
    this.positive = positive;
    this.leadingSign = leadingSign;
    this.arbitraryPrecision = arbitraryPrecision;
    this.seenChars = seenChars;
  }

  public ReadResult handle(char c) {
    if (Character.isDigit(c)) {
      ReadState nextState = new ReadInteger(this.positive, this.leadingSign, this.arbitraryPrecision, this.seenChars.add(c));
      return ReadResultFactory.notDoneYet(nextState);
    } else if (Util.isWhitespace(c)) {
      return this.finish();
    } else if (c == 'N') {
      Node result = new IntegerNode(this.positive, this.seenChars.toString(), true);
      return ReadResultFactory.done(result);
    } else {
      return null;
    }
  }

  public ReadResult finish() {
    String seenString = this.seenChars.toString();
    Node result;
    if (seenString.length() == 0 && this.leadingSign) {
      if (this.positive) {
        result = SymbolNode.fromString("+");
      } else {
        result = SymbolNode.fromString("-");
      }
    } else {
      result = new IntegerNode(this.positive, seenString, this.arbitraryPrecision);
    }
    return ReadResultFactory.done(result);
  }
}
