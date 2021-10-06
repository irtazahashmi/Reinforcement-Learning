package tudelft.rl.mysolution;

import java.util.*;

import tudelft.rl.Action;
import tudelft.rl.QLearning;
import tudelft.rl.State;

public class MyQLearning extends QLearning {

    @Override
    public void updateQ(State s, Action a, double r, State s_next, ArrayList<Action> possibleActions, double alfa, double gamma) {
        double QMax = possibleActions.stream()
                .map(action -> getQ(s_next, action))
                .max((a1, a2) -> a1 > a2 ? 1 : a1.equals(a2) ? 0 : -1)
                .get();
        double QOld = getQ(s, a);
        double QNew = QOld + alfa * (r + gamma * QMax - QOld);
        setQ(s, a, QNew);
    }

}
