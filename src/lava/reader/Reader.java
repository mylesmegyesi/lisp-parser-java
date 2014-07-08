package lava.reader;

public class Reader {

  ReadResult readString(String s, ExprReadStateFactory factory) {
    ReadState state = factory.newExprReadState();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      ReadResult result = state.handle(c);
      if (result.isFinished()) {
        if (result.isSuccess()) {
          state = new ReadWithSeenNode(result.getNodes(), factory.newExprReadState());
        } else {
          return result;
        }
      } else {
        state = result.getNextState();
      }
    }
    return state.finish();
  }

}
