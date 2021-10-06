package tudelft.rl.mysolution;

import tudelft.rl.Action;
import tudelft.rl.Agent;
import tudelft.rl.EGreedy;
import tudelft.rl.Maze;
import tudelft.rl.QLearning;

import java.util.*;

public class MyEGreedy extends EGreedy {

    @Override
    public Action getRandomAction(Agent r, Maze m) {
        Random rand = new Random();
        return m.getValidActions(r).get(rand.nextInt(m.getValidActions(r).size()));
    }

    @Override
    public Action getBestAction(Agent r, Maze m, QLearning q) {
        HashMap<Action, Double> map = new HashMap<>();

        List<Action> actions = m.getValidActions(r);
        for (Action action : actions) {
            map.put(action, q.getQ(r.getState(m), action));
        }

        double maxVal = 0;
        ArrayList<Action> listMax = new ArrayList<>();
        for (Map.Entry<Action, Double> actionDoubleEntry : map.entrySet()) {
            Double value = actionDoubleEntry.getValue();
            Action action = actionDoubleEntry.getKey();

            if (value > maxVal) {
                listMax.clear();
                listMax.add(action);
                maxVal = value;
            } else if (value == maxVal) {
                listMax.add(action);
            }
        }
        return listMax.get(new Random().nextInt(listMax.size()));
    }

    @Override
    public Action getEGreedyAction(Agent r, Maze m, QLearning q, double epsilon) {
        if (Math.random() < epsilon) {
            return getRandomAction(r, m);
        } else {
            return getBestAction(r, m, q);
        }
    }

}
