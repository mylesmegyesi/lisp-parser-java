package lava.reader;

import com.google.common.collect.ImmutableList;

public class ReadResultFactory {
  public static ReadResult notDoneYet(ReadState nextState) {
    return new NotDoneReadResult(nextState);
  }

  public static ReadResult done(ImmutableList<AstNode> nodes) {
    return new DoneReadResult(nodes);
  }

  public static ReadResult done(AstNode node) {
    return new DoneReadResult(ImmutableList.<AstNode>of(node));
  }
}
