package lava.reader;

import com.google.common.collect.ImmutableList;

public interface ReadCollectionStrategy {
  char getTerminalChar();

  AstNode createNode(ImmutableList<AstNode> seenNodes);
}
