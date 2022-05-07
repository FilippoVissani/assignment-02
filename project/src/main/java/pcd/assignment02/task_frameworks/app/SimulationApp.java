package pcd.assignment02.task_frameworks.app;

import pcd.assignment02.task_frameworks.controller.ControllerImpl;
import pcd.assignment02.task_frameworks.model.ModelImpl;

import java.util.Optional;

public class SimulationApp {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl(2);
        ControllerImpl controller = new ControllerImpl(model, 2, Optional.of(2));
        controller.startSimulation();
    }
}
