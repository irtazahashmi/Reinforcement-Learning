package tudelft.rl.mysolution;

import java.io.*;
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.Locale;

import tudelft.rl.*;

public class RunMe {

    public static void main(String[] args) {
        double epsilon;
        double alpha;
        double gamma;

        epsilon = 0.9;
        alpha = 0.7;
        gamma = 0.9;

        //load the maze
        Maze maze = new Maze(new File(System.getProperty("user.dir") + "/data/toy_maze.txt"));

        //Set the reward at the bottom right to 10
        int XGoal = 9;
        int YGoal = 9;
        maze.setR(maze.getState(XGoal, YGoal), 10);

        //Second reward
        int x_2 = 9;
        int y_2 = 0;
        maze.setR(maze.getState(x_2, y_2), 5);

        //create a robot at starting and reset location (0,0) (top left)
        Agent robot = new Agent(0, 0);

        //make a selection object (you need to implement the methods in this class)
        EGreedy selection = new MyEGreedy();

        //make a Qlearning object (you need to implement the methods in this class)
        QLearning learn = new MyQLearning();

        boolean stop = false;
        int i = 0;
        int trail = 0;

        //keep learning until you decide to stop
        while (!stop) {
            if (i == 100000) {
                stop = true;
            }

            Action action = selection.getEGreedyAction(robot, maze, learn, epsilon);
            State state = robot.getState(maze);

            State nState = robot.doAction(action, maze);
            double r = maze.getR(nState);
            learn.updateQ(state, action, r, nState, maze.getValidActions(robot), alpha, gamma);

            if ((robot.x == XGoal && robot.y == YGoal) || (robot.x == x_2 && robot.y == y_2)) {
                trail++;
                robot.reset();

                //Decrementing the epsilon after each trial
                if (epsilon < 0) {
                    epsilon = 0;
                } else if (trail > 30) {
                    epsilon -= 0.01;
                }

            }
            i++;
        }
    }
}

