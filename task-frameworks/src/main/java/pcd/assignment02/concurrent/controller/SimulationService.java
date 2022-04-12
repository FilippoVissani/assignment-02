package pcd.assignment02.concurrent.controller;

import pcd.assignment02.concurrent.controller.task.UpdatePositionTask;
import pcd.assignment02.concurrent.controller.task.UpdateSpeedTask;
import pcd.assignment02.concurrent.model.Model;
import pcd.assignment02.concurrent.util.Logger;
import pcd.assignment02.concurrent.util.Pair;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class used to manage the simulation
 */
public class SimulationService implements Runnable {

    private final Model model;
    private final ViewController controller;
    private final long iterations;
    //private final Chronometer chronometer;
    private final AtomicBoolean stop;
    private final int threadsNumber;
    private final Map<String, List<Callable<Boolean>>> tasks;
    private final ExecutorService executor;

    public SimulationService(final Model model, final ViewController controller, final long iterations, final Optional<Integer> poolSize) {
        this.model = model;
        this.controller = controller;
        this.iterations = iterations;
        //this.chronometer = new ChronometerImpl();
        this.stop = new AtomicBoolean(false);
        this.threadsNumber = poolSize.isPresent() ? poolSize.get() : Runtime.getRuntime().availableProcessors() + 1;
        this.executor = Executors.newFixedThreadPool(threadsNumber);
        this.tasks = this.generateTasks(this.threadsNumber);
    }

    @Override
    public void run() {
        //Logger.logSimulationStarted();
        long iteration = 0;
        //this.chronometer.start();
        while (iteration < iterations && !this.stop.get()) {
            try {
                for (Future<Boolean> result : executor.invokeAll(tasks.get(UpdateSpeedTask.class.getName()))){
                    result.get();
                }
                for (Future<Boolean> result : executor.invokeAll(tasks.get(UpdatePositionTask.class.getName()))){
                    result.get();
                }
                this.model.incrementVirtualTime();
                this.controller.updateView(iteration);
                iteration = iteration + 1;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //this.chronometer.stop();
        this.executor.shutdown();
        /*
        Logger.logSimulationResult(model.getBodiesPositions().size(),
                iterations,
                this.chronometer.getTime(),
                this.threadsNumber);
        Logger.logProgramTerminated();*/
    }

    public synchronized void stopSimulation() {
        this.stop.set(true);
    }

    private Map<String, List<Callable<Boolean>>> generateTasks(final int threadsNumber){
        List<Callable<Boolean>> updateSpeedTasks = new ArrayList<>();
        List<Callable<Boolean>> updatePositionTasks = new ArrayList<>();
        Map<String, List<Callable<Boolean>>> taskMap = new HashMap<>();
        taskMap.put(UpdatePositionTask.class.getName(), updatePositionTasks);
        taskMap.put(UpdateSpeedTask.class.getName(), updateSpeedTasks);
        int range = model.getBodiesNumber() / threadsNumber;
        int last = 0;
        for (int i = 0; i < threadsNumber - 1; i++){
            last = i * range + range;
            updateSpeedTasks.add(new UpdateSpeedTask(this.model, new Pair<>(i * range, last)));
            updatePositionTasks.add(new UpdatePositionTask(this.model, new Pair<>(i * range, last)));
        }
        updateSpeedTasks.add(new UpdateSpeedTask(this.model, new Pair<>(last, this.model.getBodiesNumber())));
        updatePositionTasks.add(new UpdatePositionTask(this.model, new Pair<>(last, this.model.getBodiesNumber())));
        return taskMap;
    }
}
