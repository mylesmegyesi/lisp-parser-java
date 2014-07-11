package lava.reader;

import com.google.common.collect.ImmutableList;

public class SetReadCollectionStrategy implements ReadCollectionStrategy {

  public char getTerminalChar() {
    return '}';
  }

  public AstNode createNode(ImmutableList<AstNode> seenNodes) {
    return new SetNode(seenNodes);
  }
}
