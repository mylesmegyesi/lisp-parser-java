package lava.reader;

public class ReadFloatExponentAfterFirst implements ReadState {

  private ParentReadState parentReadState;
  private boolean positive;
  private boolean exponentPositive;
  private String intValueAsString;
  private String decimalValueAsString;
  private SeenChars seenExponentChars;

  public ReadFloatExponentAfterFirst(ParentReadState parentReadState, boolean positive, String intValueAsString, String decimalValueAsString, boolean exponentPositive) {
    this(parentReadState, positive, intValueAsString, decimalValueAsString, exponentPositive, new SeenChars());
  }

  public ReadFloatExponentAfterFirst(ParentReadState parentReadState, boolean positive, String intValueAsString, String decimalValueAsString, boolean exponentPositive, SeenChars seenExponentChars) {
    this.parentReadState = parentReadState;
    this.positive = positive;
    this.intValueAsString = intValueAsString;
    this.decimalValueAsString = decimalValueAsString;
    this.exponentPositive = exponentPositive;
    this.seenExponentChars = seenExponentChars;
  }

  public ReadResult handle(char c) {
    if (Character.isDigit(c)) {
      return ReadResultFactory.notDoneYet(new ReadFloatExponentAfterFirst(this.parentReadState, this.positive, this.intValueAsString, this.decimalValueAsString, this.exponentPositive, this.seenExponentChars.add(c)));
    } else if (Util.isWhitespace(c)) {
      return ReadResultFactory.done(new FloatNode(this.positive, this.intValueAsString, this.decimalValueAsString, this.seenExponentChars.toString(), this.exponentPositive));
    } else {
      // unexpected char
      return null;
    }
  }

  public ReadResult finish() {
    String exponentValueAsString = this.seenExponentChars.toString();
    if (exponentValueAsString.length() == 0) {
      return ReadResultFactory.notDoneYet(this);
    } else {
      return ReadResultFactory.done(new FloatNode(this.positive, this.intValueAsString, this.decimalValueAsString, exponentValueAsString, this.exponentPositive));
    }
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }
}
