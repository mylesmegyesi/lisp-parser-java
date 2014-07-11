package lava.reader;

import com.google.common.collect.ImmutableList;

public class VectorNode implements AstNode {

  private ImmutableList<AstNode> nodes;

  public VectorNode(ImmutableList<AstNode> nodes) {
    this.nodes = nodes;
  }

  public ImmutableList<AstNode> getNodes() {
    return this.nodes;
  }
}
