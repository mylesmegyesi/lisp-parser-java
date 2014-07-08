package lava.reader;

public class LavaReader {
  ExprReadStateFactory lavaExprStateFactory;
  Reader reader;

  LavaReader() {
    this.reader = new Reader();
    this.lavaExprStateFactory = new LavaExprReadStateFactory();
  }

  public ReadResult readString(String s) {
    return this.reader.readString(s, this.lavaExprStateFactory);
  }
}
