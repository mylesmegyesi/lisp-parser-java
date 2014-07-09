package lava.reader;

import lava.util.ImmutableList;

public class ListReadCollectionStrategy implements ReadCollectionStrategy {

  public char getTerminalChar() {
    return ')';
  }

  public AstNode createNode(ImmutableList<AstNode> seenNodes) {
    return new ListNode(seenNodes);
  }
}
