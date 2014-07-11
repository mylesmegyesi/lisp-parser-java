package lava.reader;

public class ReadString implements ReadState, ParentReadState {

  private ParentReadState parentReadState;
  private SeenChars seenChars;

  public ReadString(ParentReadState parentReadState) {
    this(parentReadState, new SeenChars());
  }

  public ReadString(ParentReadState parentReadState, SeenChars seenChars) {
    this.parentReadState = parentReadState;
    this.seenChars = seenChars;
  }

  public ReadResult handle(char c) {
    if (c == '"') {
      return ReadResultFactory.done(new StringNode(this.seenChars.toString()));
    } else if (c == '\\') {
      return ReadResultFactory.notDoneYet(new ReadStringEscape(this.parentReadState, this, this.seenChars));
    } else {
      return ReadResultFactory.notDoneYet(new ReadString(this.parentReadState, this.seenChars.add(c)));
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.notDoneYet(new ReadString(this.parentReadState, this.seenChars));
  }

  public ParentReadState getParentReadState() {
    return this.parentReadState;
  }

  public boolean terminal(char c) {
    return c == '"';
  }
}
