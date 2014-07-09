package lava.reader;

public class ReadFloatDecimal implements ReadState {

  private ParentReadState parentReadState;
  private boolean positive;
  private String intValueAsString;
  private SeenChars seenDecimalChars;

  public ReadFloatDecimal(ParentReadState parentReadState, boolean positive, String intValueAsString) {
    this(parentReadState, positive, intValueAsString, new SeenChars());
  }

  public ReadFloatDecimal(ParentReadState parentReadState, boolean positive, String intValueAsString, SeenChars seenDecimalChars) {
    this.parentReadState = parentReadState;
    this.positive = positive;
    this.intValueAsString = intValueAsString;
    this.seenDecimalChars = seenDecimalChars;
  }

  public ReadResult handle(char c) {
    if (Character.isDigit(c)) {
      return ReadResultFactory.notDoneYet(new ReadFloatDecimal(this.parentReadState, this.positive, this.intValueAsString, this.seenDecimalChars.add(c)));
    } else if (Util.isWhitespace(c) || this.parentReadState.terminal(c)) {
      return this.finish();
    } else if (c == 'e') {
      return ReadResultFactory.notDoneYet(new ReadFloatExponentFirstChar(this.parentReadState, this.positive, this.intValueAsString, this.seenDecimalChars.toString()));
    } else if (c == 'E') {
      return ReadResultFactory.notDoneYet(new ReadFloatExponentFirstChar(this.parentReadState, this.positive, this.intValueAsString, this.seenDecimalChars.toString()));
    } else {
      return null;
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(new FloatNode(this.positive, this.intValueAsString, this.seenDecimalChars.toString(), "", true));
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }
}
