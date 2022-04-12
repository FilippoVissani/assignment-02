package concurrent.simulation;

import concurrent.controller.ControllerImpl;
import concurrent.model.ModelImpl;
import java.util.Optional;

public class SimulationApp {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl(100);
        ControllerImpl controller = new ControllerImpl(model, 1000, Optional.empty());
        controller.startSimulation();
    }
}
