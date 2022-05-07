package pcd.assignment02.task_frameworks.controller;

import pcd.assignment02.task_frameworks.view.View;

/**
 * Interface used to represent the controller in MVC
 */
public interface Controller extends ViewController {

    /**
     * @param view
     * Set the view of the controller
     */
    void setView(View view);

    /**
     * Start the simulation
     */
    void startSimulation();

    /**
     * Stop the simulation
     */
    void stopSimulation();
}
