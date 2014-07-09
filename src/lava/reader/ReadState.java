package lava.reader;

public interface ReadState {

  public ReadResult handle(char c);

  public ReadResult finish();

  public ParentReadState getParentReadState();
}
