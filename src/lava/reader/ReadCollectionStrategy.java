package lava.reader;

import lava.util.ImmutableList;

public interface ReadCollectionStrategy {
  char getTerminalChar();

  AstNode createNode(ImmutableList<AstNode> seenNodes);
}
