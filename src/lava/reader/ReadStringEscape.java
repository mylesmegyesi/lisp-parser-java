package lava.reader;

public class ReadStringEscape implements ReadState {

  private ParentReadState stringParent;
  private ParentReadState parentReadState;
  private SeenChars seenChars;

  public ReadStringEscape(ParentReadState stringParent, ParentReadState parentReadState, SeenChars seenChars) {
    this.stringParent = stringParent;
    this.parentReadState = parentReadState;
    this.seenChars = seenChars;
  }

  public ReadResult handle(char c) {
    switch (c) {
      case 't':
        return continueWith('\t');
      case 'b':
        return continueWith('\b');
      case 'n':
        return continueWith('\n');
      case 'r':
        return continueWith('\r');
      case 'f':
        return continueWith('\f');
      case 'u':
        return ReadResultFactory.notDoneYet(new ReadCharacterSequence(16, 4, this.stringParent, this.parentReadState, this.seenChars));
      case '\'':
        return continueWith('\'');
      case '\"':
        return continueWith('\"');
      case '\\':
        return continueWith('\\');
      default:
        if (Character.isDigit(c)) {
          return new ReadCharacterSequence(8, 3, this.stringParent, this.parentReadState, this.seenChars).handle(c);
        } else {
          return null;
        }
    }
  }

  public ReadResult finish() {
    return null;
  }

  public ParentReadState getParentReadState() {
    return null;
  }

  private ReadResult continueWith(char c) {
    return ReadResultFactory.notDoneYet(new ReadString(this.stringParent, this.seenChars.add(c)));
  }
}
