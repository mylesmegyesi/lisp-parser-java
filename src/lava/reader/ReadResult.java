package lava.reader;

import lava.util.ImmutableList;

public interface ReadResult {
  boolean isSuccess();

  boolean isFinished();

  ReadState getNextState();

  ImmutableList<Node> getNodes();

  ReadError getReadError();
}
