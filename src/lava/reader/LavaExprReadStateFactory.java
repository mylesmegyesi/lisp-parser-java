package lava.reader;

public class LavaExprReadStateFactory implements ExprReadStateFactory {

  public ReadState newExprReadState(ParentReadState parentReadState) {
    return new ReadLavaExpr(this, parentReadState);
  }

}
