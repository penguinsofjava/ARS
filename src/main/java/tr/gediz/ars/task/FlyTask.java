package tr.gediz.ars.task;

import tr.gediz.ars.logic.PlaneMath;
import tr.gediz.ars.model.Plane;
import tr.gediz.ars.view.PlaneView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A {@link TimerTask} that makes a {@link Plane} fly on the map.
 */
public class FlyTask extends TimerTask {
    private PlaneView planeView;

    public static FlyTask with(PlaneView planeView) {
        return new FlyTask(planeView);
    }

    private FlyTask(PlaneView planeView) {
        this.planeView = planeView;
    }

    @Override
    public void run() {
        planeView.moveInX(planeView.getPlane().getSpeed());
        planeView.setY(
                PlaneMath.getYCoordinate(
                        planeView.getPosition().getX(),
                        planeView.getPlane().getPathType(),
                        planeView.getPlane().getYAnchor(),
                        planeView.getPlane().getPathExtensionConstant()
                )
        );
    }
}