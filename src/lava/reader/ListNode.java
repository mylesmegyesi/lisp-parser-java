package lava.reader;

import com.google.common.collect.ImmutableList;

public class ListNode implements AstNode {

  private ImmutableList<AstNode> nodes;

  public ListNode(ImmutableList<AstNode> nodes) {
    this.nodes = nodes;
  }

  public ImmutableList<AstNode> getNodes() {
    return this.nodes;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("(");
    int lastIndex = this.nodes.size() - 1;
    for (int i = 0; i < this.nodes.size(); i++) {
      sb.append(this.nodes.get(i).toString());
      if (i != lastIndex) {
        sb.append(" ");
      }
    }
    sb.append(")");
    return sb.toString();
  }
}
