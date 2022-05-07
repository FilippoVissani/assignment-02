package pcd.assignment02.task_frameworks.app;

import pcd.assignment02.task_frameworks.controller.ControllerImpl;
import pcd.assignment02.task_frameworks.model.ModelImpl;
import pcd.assignment02.task_frameworks.view.GraphicalView;

import java.util.Optional;

public class SimulationAppGUI {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl(5000);
        ControllerImpl controller = new ControllerImpl(model, 1000, Optional.empty());
        GraphicalView view = new GraphicalView(controller, 820, 820);
        controller.setView(view);
    }
}
