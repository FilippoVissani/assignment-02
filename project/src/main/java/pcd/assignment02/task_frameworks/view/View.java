package pcd.assignment02.task_frameworks.view;

import pcd.assignment02.task_frameworks.util.Boundary;
import pcd.assignment02.task_frameworks.util.Point2D;

import java.util.List;

/**
 * Interface used to represent view in MVC
 */
public interface View {
    /**
     * @param bodiesPositions current position of the bodies
     * @param virtualTime actual virtual time
     * @param currentIteration current iteration
     * @param bounds bounds of the simulation
     *
     */
    void display(List<Point2D> bodiesPositions, double virtualTime, long currentIteration, Boundary bounds);

    /**
     * Stop the simulation
     */
    void stopSimulation();

    /**
     * Start the simulation
     */
    void startSimulation();
}
