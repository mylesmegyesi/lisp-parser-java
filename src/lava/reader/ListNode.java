package lava.reader;

import lava.util.ImmutableList;

public class ListNode implements AstNode {

  private ImmutableList<AstNode> nodes;

  public ListNode(ImmutableList<AstNode> nodes) {
    this.nodes = nodes;
  }

  public ImmutableList<AstNode> getNodes() {
    return this.nodes;
  }
}
