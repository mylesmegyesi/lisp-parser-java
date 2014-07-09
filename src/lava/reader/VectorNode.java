package lava.reader;

import lava.util.ImmutableList;

public class VectorNode implements AstNode {

  private ImmutableList<AstNode> nodes;

  public VectorNode(ImmutableList<AstNode> nodes) {
    this.nodes = nodes;
  }

  public ImmutableList<AstNode> getNodes() {
    return this.nodes;
  }
}
