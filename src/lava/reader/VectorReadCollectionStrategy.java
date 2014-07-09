package lava.reader;

import lava.util.ImmutableList;

public class VectorReadCollectionStrategy implements ReadCollectionStrategy {
  public char getTerminalChar() {
    return ']';
  }

  public AstNode createNode(ImmutableList<AstNode> seenNodes) {
    return new VectorNode(seenNodes);
  }
}
