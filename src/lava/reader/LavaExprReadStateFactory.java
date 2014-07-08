package lava.reader;

public class LavaExprReadStateFactory implements ExprReadStateFactory {

  public ReadState newExprReadState() {
    return new ReadLavaExpr();
  }

}
