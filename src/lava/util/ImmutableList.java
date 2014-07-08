package lava.util;

public interface ImmutableList<T> extends Iterable<T> {
  public ImmutableList<T> append(T item);

  public ImmutableList<T> append(ImmutableList<T> items);

  public T first() throws IndexOutOfBoundsException;

  public int size();

  public T get(int i) throws IndexOutOfBoundsException;
}
