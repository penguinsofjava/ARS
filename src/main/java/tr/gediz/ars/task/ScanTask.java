package tr.gediz.ars.task;

import tr.gediz.ars.controller.MainController;
import tr.gediz.ars.logic.RadarMath;
import tr.gediz.ars.coordinator.PlaneCoordinator;
import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.model.Threat;
import tr.gediz.ars.view.PlaneView;

import javax.swing.text.html.ListView;
import java.util.ConcurrentModificationException;
import java.util.TimerTask;

/**
 * A {@link TimerTask} that performs a scan for {@link Plane}s on the map to see whether they are friendly or hostile.
 * Upon finding a hostile plane (i.e., {@link Threat}), it adds it to {@link MainController#threats}, which in turn
 * displays the {@link Threat} on a {@link ListView} to the right of the map.
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
                        PlaneView planeView = MainController.getInstance().getPlaneView(plane);
                        planeView.setBeingTracked(true);
                        MainController.getInstance().addThreat(new Threat(radar, plane));
                    }

                    MainController.getInstance().logMessage(
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