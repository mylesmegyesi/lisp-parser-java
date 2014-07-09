package lava.reader;

public class Reader {

  ReadResult readString(String s, ExprReadStateFactory factory) {
    ParentReadState parentReadState = new RootParentState();
    ReadState state = factory.newExprReadState(parentReadState);
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      ReadResult result = state.handle(c);
      if (result.isFinished()) {
        if (result.isSuccess()) {
          state = new ReadWithSeenNode(parentReadState, result.getNodes(), factory.newExprReadState(parentReadState));
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
