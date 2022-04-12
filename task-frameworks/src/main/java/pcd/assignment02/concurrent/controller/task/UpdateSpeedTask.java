package pcd.assignment02.concurrent.controller.task;

import pcd.assignment02.concurrent.model.Model;
import pcd.assignment02.concurrent.model.Vector2D;
import pcd.assignment02.concurrent.util.Pair;

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
