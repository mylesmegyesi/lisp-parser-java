package lava.reader;

public class ReadInteger implements ReadState {

  private ParentReadState parentReadState;
  private boolean positive;
  private boolean leadingSign;
  private boolean arbitraryPrecision;
  private SeenChars seenChars;

  public ReadInteger(ParentReadState parentReadState, boolean positive, boolean leadingSign) {
    this(parentReadState, positive, leadingSign, false, new SeenChars());
  }

  public ReadInteger(ParentReadState parentReadState, boolean positive, boolean leadingSign, boolean arbitraryPrecision, SeenChars seenChars) {
    this.parentReadState = parentReadState;
    this.positive = positive;
    this.leadingSign = leadingSign;
    this.arbitraryPrecision = arbitraryPrecision;
    this.seenChars = seenChars;
  }

  public ReadResult handle(char c) {
    if (Character.isDigit(c)) {
      ReadState nextState = new ReadInteger(this.parentReadState, this.positive, this.leadingSign, this.arbitraryPrecision, this.seenChars.add(c));
      return ReadResultFactory.notDoneYet(nextState);
    } else if (Util.isWhitespace(c)) {
      return this.finish();
    } else if (c == 'N') {
      AstNode result = new IntegerNode(this.positive, this.seenChars.toString(), true);
      return ReadResultFactory.done(result);
    } else if (c == 'M') {
      AstNode result = new FloatNode(this.positive, this.seenChars.toString(), "", "", true);
      return ReadResultFactory.done(result);
    } else if (c == '.') {
      return ReadResultFactory.notDoneYet(new ReadFloatDecimal(this.parentReadState, this.positive, this.seenChars.toString()));
    } else {
      return null;
    }
  }

  public ReadResult finish() {
    String seenString = this.seenChars.toString();
    AstNode result;
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

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }
}
