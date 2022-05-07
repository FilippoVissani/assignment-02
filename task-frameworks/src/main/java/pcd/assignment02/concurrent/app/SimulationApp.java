package pcd.assignment02.concurrent.app;

import pcd.assignment02.concurrent.controller.ControllerImpl;
import pcd.assignment02.concurrent.model.ModelImpl;

import java.util.Optional;

public class SimulationApp {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl(2);
        ControllerImpl controller = new ControllerImpl(model, 2, Optional.of(2));
        controller.startSimulation();
    }
}
