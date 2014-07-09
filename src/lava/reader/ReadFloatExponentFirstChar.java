package lava.reader;

public class ReadFloatExponentFirstChar implements ReadState {

  private ParentReadState parentReadState;
  private boolean positive;
  private String intValueAsString;
  private String decimalValueAsString;

  public ReadFloatExponentFirstChar(ParentReadState parentReadState, boolean positive, String intValueAsString, String decimalValueAsString) {
    this.parentReadState = parentReadState;
    this.positive = positive;
    this.intValueAsString = intValueAsString;
    this.decimalValueAsString = decimalValueAsString;
  }

  public ReadResult handle(char c) {
    if (Character.isDigit(c)) {
      return new ReadFloatExponentAfterFirst(this.parentReadState, this.positive, this.intValueAsString, this.decimalValueAsString, true).handle(c);
    } else if (c == '+') {
      return ReadResultFactory.notDoneYet(new ReadFloatExponentAfterFirst(this.parentReadState, this.positive, this.intValueAsString, this.decimalValueAsString, true));
    } else if (c == '-') {
      return ReadResultFactory.notDoneYet(new ReadFloatExponentAfterFirst(this.parentReadState, this.positive, this.intValueAsString, this.decimalValueAsString, false));
    } else {
      // unexpected char
      return null;
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.notDoneYet(this);
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }
}
