package lava.reader;

import com.google.common.collect.ImmutableList;

public class SetNode implements AstNode {

  private ImmutableList<AstNode> nodes;

  public SetNode(ImmutableList<AstNode> nodes) {
    this.nodes = nodes;
  }

  public ImmutableList<AstNode> getNodes() {
    return this.nodes;
  }
}
