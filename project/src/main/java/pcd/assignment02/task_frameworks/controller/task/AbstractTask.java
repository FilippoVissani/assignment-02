package pcd.assignment02.task_frameworks.controller.task;

import pcd.assignment02.task_frameworks.model.Model;
import pcd.assignment02.task_frameworks.util.Pair;

import java.util.concurrent.Callable;

public abstract class AbstractTask implements Callable<Boolean> {
    private final Model model;
    private final Pair<Integer, Integer> range;

    public AbstractTask(final Model model, final Pair<Integer, Integer> range) {
        this.model = model;
        this.range = range;
    }

    protected Model getModel() {
        return model;
    }

    protected Pair<Integer, Integer> getRange() {
        return range;
    }
}
