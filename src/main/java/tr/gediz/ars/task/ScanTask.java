package tr.gediz.ars.task;

import tr.gediz.ars.logic.RadarMath;
import tr.gediz.ars.coordinator.PlaneCoordinator;
import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.model.Threat;
import tr.gediz.ars.view.PlaneView;

import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import static tr.gediz.ars.Main.getController;

/**
 * A {@link TimerTask} that performs a task for {@link Plane}s on the "map" to see whether they are friendly or hostile
 * and kicks off some notifications and stuff...
 * <p>
 * Make sure to cancel the parent {@link Timer} once you are done with it!
 */
public class ScanTask extends TimerTask {
    private Radar radar;

    public static ScanTask with(Radar radar) {
        return new ScanTask(radar);
    }

    private ScanTask(Radar radar) {
        this.radar = radar;
    }

    @Override
    public void run() {
        try {
            for (Plane plane : PlaneCoordinator.getPlanes()) {
                if (RadarMath.isPlaneInTheRadar(plane, radar)) {
                    /* Notify plane so that it performs the necessary visual feedback */
                    if (plane.getListener() != null) {
                        plane.getListener().onCaughtOnRadar(radar);
                    }

                    if (plane.getType() == Plane.Type.HOSTILE) {
                        PlaneView planeView = getController().getPlaneView(plane);
                        planeView.setBeingTracked(true);
                        getController().addThreat(new Threat(radar, plane));
                    }

                    getController().logMessage(
                            "Radar \"" + radar.getName() + "\" detected "
                                    + plane.getType().toString() + " plane at (" + plane.getPosition().getX() + ", " + plane.getPosition().getY() + ")"
                    );
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }
}