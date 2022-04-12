package pcd.assignment02.concurrent.controller;

import pcd.assignment02.concurrent.model.Model;
import pcd.assignment02.concurrent.view.View;
import java.util.Optional;

/**
 * Implementation of Controller
 */
public class ControllerImpl implements Controller{
    private final Model model;
    private Optional<View> view;
    private final SimulationService simulationService;

    public ControllerImpl(final Model model, final long iterations, final Optional<Integer> workersNumber) {
        this.model = model;
        this.simulationService = new SimulationService(model, this, iterations, workersNumber);
        this.view = Optional.empty();
    }

    @Override
    public void setView(final View view) {
        this.view = Optional.of(view);
    }

    @Override
    public synchronized void updateView(long currentIteration){
        if (this.view.isPresent()){
            view.get().display(model.getBodiesPositions(), model.getVirtualTime(), currentIteration, model.getBounds());
        }
    }

    @Override
    public void startSimulation() {
        new Thread(this.simulationService, "MASTER").start();
    }

    @Override
    public void stopSimulation() {
        this.simulationService.stopSimulation();
    }
}
