package pcd.assignment02.task_frameworks.controller.task;

import pcd.assignment02.task_frameworks.model.Model;
import pcd.assignment02.task_frameworks.model.Vector2D;
import pcd.assignment02.task_frameworks.util.Pair;

import java.util.List;

public class UpdateSpeedTask extends AbstractTask {

    public UpdateSpeedTask(final Model model, final Pair<Integer, Integer> range) {
        super(model, range);
    }

    @Override
    public Boolean call() throws Exception {
        List<Vector2D> acceleration = this.getModel().computeAccelerationOnBodiesRange(this.getRange());
        this.getModel().updateSpeedOnBodiesRange(this.getRange(), acceleration);
        return true;
    }
}
