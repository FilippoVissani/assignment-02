package pcd.assignment02.task_frameworks.view;

import pcd.assignment02.task_frameworks.controller.Controller;
import pcd.assignment02.task_frameworks.util.Boundary;
import pcd.assignment02.task_frameworks.util.Point2D;

import java.util.List;

/**
 * Implementation of View interface
 */
public class GraphicalView implements View {

    private final Controller controller;
    private final SimulationGUI frame;

    public GraphicalView(final Controller controller, int width, int height){
        this.controller = controller;
        frame = new SimulationGUI(width,height, this);
    }

    @Override
    public void display(final List<Point2D> bodiesPositions, final double virtualTime, final long currentIteration, final Boundary bounds) {
        frame.display(bodiesPositions, virtualTime, currentIteration, bounds);
    }

    @Override
    public void stopSimulation() {
        this.controller.stopSimulation();
    }

    @Override
    public void startSimulation(){
        this.controller.startSimulation();
    }
}
