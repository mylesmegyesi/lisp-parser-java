package lava.reader;

public class ReadCharacterSequence implements ReadState {

  private int radix;
  private int maxLength;
  private ParentReadState stringParent;
  private ParentReadState parentReadState;
  private SeenChars seenChars;
  private SeenChars seenUnicodeChars;

  public ReadCharacterSequence(int radix, int maxLength, ParentReadState stringParent, ParentReadState parentReadState, SeenChars seenChars) {
    this(radix, maxLength, stringParent, parentReadState, seenChars, new SeenChars());
  }

  public ReadCharacterSequence(int radix, int maxLength, ParentReadState stringParent, ParentReadState parentReadState, SeenChars seenChars, SeenChars seenUnicodeChars) {
    this.radix = radix;
    this.maxLength = maxLength;
    this.stringParent = stringParent;
    this.parentReadState = parentReadState;
    this.seenChars = seenChars;
    this.seenUnicodeChars = seenUnicodeChars;
  }

  public ReadResult handle(char c) {
    if (this.parentReadState.terminal(c)) {
      char readChar = seenToChar(this.seenUnicodeChars);
      return new ReadString(this.stringParent, this.seenChars.add(readChar)).handle(c);
    } else {
      if (Character.digit(c, this.radix) != -1) {
        SeenChars currentSeenChars = this.seenUnicodeChars.add(c);
        if (currentSeenChars.size() == this.maxLength) {
          char readChar = seenToChar(currentSeenChars);
          return ReadResultFactory.notDoneYet(new ReadString(this.stringParent, this.seenChars.add(readChar)));
        } else {
          return ReadResultFactory.notDoneYet(new ReadCharacterSequence(this.radix, this.maxLength, this.stringParent, this.parentReadState, this.seenChars, currentSeenChars));
        }
      } else {
        // invalid unicode character
        return null;
      }
    }
  }

  public ReadResult finish() {
    return null;
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }

  private char seenToChar(SeenChars seen) {
    int unicodeChar = Character.digit(seen.get(0), this.radix);
    for (int i = 1; i < seen.size(); i++) {
      unicodeChar = (unicodeChar * this.radix) + Character.digit(seen.get(i), this.radix);
    }
    return (char) unicodeChar;
  }
}
