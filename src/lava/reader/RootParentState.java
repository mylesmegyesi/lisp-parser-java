package lava.reader;

public class RootParentState implements ParentReadState {
  public boolean terminal(char c) {
    return false;
  }
}
