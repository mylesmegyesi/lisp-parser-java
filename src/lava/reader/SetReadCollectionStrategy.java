package lava.reader;

import lava.util.ImmutableList;

public class SetReadCollectionStrategy implements ReadCollectionStrategy {

  public char getTerminalChar() {
    return '}';
  }

  public AstNode createNode(ImmutableList<AstNode> seenNodes) {
    return new SetNode(seenNodes);
  }
}
