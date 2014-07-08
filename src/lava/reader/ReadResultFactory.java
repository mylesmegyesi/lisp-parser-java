package lava.reader;

import lava.util.ImmutableArrayList;
import lava.util.ImmutableList;

public class ReadResultFactory {
  public static ReadResult notDoneYet(ReadState nextState) {
    return new NotDoneReadResult(nextState);
  }

  public static ReadResult done(ImmutableList<Node> nodes) {
    return new DoneReadResult(nodes);
  }

  public static ReadResult done(Node node) {
    ImmutableList<Node> nodes = new ImmutableArrayList<Node>();
    return new DoneReadResult(nodes.append(node));
  }
}
