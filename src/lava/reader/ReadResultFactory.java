package lava.reader;

import lava.util.ImmutableArrayList;
import lava.util.ImmutableList;

public class ReadResultFactory {
  public static ReadResult notDoneYet(ReadState nextState) {
    return new NotDoneReadResult(nextState);
  }

  public static ReadResult done(ImmutableList<AstNode> nodes) {
    return new DoneReadResult(nodes);
  }

  public static ReadResult done(AstNode node) {
    ImmutableList<AstNode> nodes = new ImmutableArrayList<AstNode>();
    return new DoneReadResult(nodes.append(node));
  }
}
