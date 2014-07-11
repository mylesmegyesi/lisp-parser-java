package lava.reader;

import com.google.common.collect.ImmutableList;

public interface ReadResult {
  boolean isSuccess();

  boolean isFinished();

  ReadState getNextState();

  ImmutableList<AstNode> getNodes();

  ReadError getReadError();
}
