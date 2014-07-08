package lava.reader;

import lava.util.ImmutableArrayList;

public class ReadLavaExpr implements ReadState {

  public ReadLavaExpr() {
  }

  public ReadResult handle(char c) {
    if (Util.isWhitespace(c)) {
      return ReadResultFactory.notDoneYet(new ReadLavaExpr());
    } else if (Character.isDigit(c)) {
      return new ReadInteger(true, false).handle(c);
    } else if (c == ':') {
      return ReadResultFactory.notDoneYet(new ReadKeyword());
    } else if (c == '+') {
      return ReadResultFactory.notDoneYet(new ReadInteger(true, true));
    } else if (c == '-') {
      return ReadResultFactory.notDoneYet(new ReadInteger(false, true));
    } else {
      return new ReadSymbol().handle(c);
    }
  }

  public ReadResult finish() {
    return ReadResultFactory.done(new ImmutableArrayList<Node>());
  }

}
