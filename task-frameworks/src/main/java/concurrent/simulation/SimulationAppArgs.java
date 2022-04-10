package concurrent.simulation;

import concurrent.controller.ControllerImpl;
import concurrent.model.ModelImpl;
import java.util.Optional;

public class SimulationAppArgs {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl(Integer.parseInt(args[0]));
        if (args.length == 3){
            ControllerImpl controller = new ControllerImpl(model, Long.parseLong(args[1]), Optional.of(Integer.parseInt(args[2])));
            controller.startSimulation();
        } else {
            ControllerImpl controller = new ControllerImpl(model, Long.parseLong(args[1]), Optional.empty());
            controller.startSimulation();
        }
    }
}
