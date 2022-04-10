package concurrent.simulation;

import concurrent.controller.ControllerImpl;
import concurrent.model.ModelImpl;
import concurrent.view.GraphicalView;

import java.util.Optional;

public class SimulationAppGUI {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl(100);
        ControllerImpl controller = new ControllerImpl(model, 1000, Optional.empty());
        GraphicalView view = new GraphicalView(controller, 820, 820);
        controller.setView(view);
    }
}
