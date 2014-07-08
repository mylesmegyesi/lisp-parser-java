package lava.util;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ImmutableArrayList<T> implements ImmutableList<T> {

  private int size;
  private T[] items;

  public ImmutableArrayList() {
    this.size = 0;
    this.items = null;
  }

  private ImmutableArrayList(int size, T[] items) {
    this.size = size;
    this.items = items;
  }

  public ImmutableList<T> append(T item) {
    T[] newArr;
    if (this.size == 0) {
      newArr = (T[]) Array.newInstance(item.getClass(), 1);
    } else {
      newArr = (T[]) Array.newInstance(item.getClass(), this.size + 1);
      System.arraycopy(this.items, 0, newArr, 0, this.size);
    }
    newArr[this.size] = item;
    return new ImmutableArrayList<T>(this.size + 1, newArr);
  }

  public ImmutableList<T> append(ImmutableList<T> items) {
    ImmutableList<T> tmp = this;
    for (T item : items) {
      tmp = tmp.append(item);
    }
    return tmp;
  }

  public T first() throws IndexOutOfBoundsException {
    return this.get(0);
  }

  public T get(int index) throws IndexOutOfBoundsException {
    if ((index < 0 || index >= this.size)) {
      throw new IndexOutOfBoundsException();
    }
    return this.items[index];
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < this.size; i++) {
      T item = this.get(i);
      if (item != null) {
        sb.append(item.toString());
      } else {
        sb.append("null");
      }
      if (i != this.size - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }

  public Iterator<T> iterator() {
    return new ImmutableArrayListIterator<T>(this);
  }

  public int size() {
    return this.size;
  }

  private class ImmutableArrayListIterator<T> implements Iterator<T> {

    private ImmutableArrayList<T> immutableArray;
    private int i;

    ImmutableArrayListIterator(ImmutableArrayList<T> immutableArray) {
      this.immutableArray = immutableArray;
      this.i = 0;
    }

    public boolean hasNext() {
      return i < this.immutableArray.size();
    }

    public T next() {
      T item = this.immutableArray.get(this.i);
      this.i++;
      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
