package pcd.assignment02.concurrent.controller.task;

import pcd.assignment02.concurrent.model.Model;
import pcd.assignment02.concurrent.util.Pair;

public class UpdatePositionTask extends AbstractTask {

    public UpdatePositionTask(final Model model, final Pair<Integer, Integer> range) {
        super(model, range);
    }

    @Override
    public Boolean call() throws Exception {
        this.getModel().updatePositionOnBodiesRange(this.getRange());
        this.getModel().checkAndSolveBoundaryCollisionOnBodiesRange(this.getRange());
        return true;
    }
}
