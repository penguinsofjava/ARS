package sample.task;

import sample.coordinator.PlaneCoordinator;
import sample.logic.RadarMath;
import sample.model.Plane;
import sample.model.Radar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A {@link TimerTask} that performs a task for {@link Plane}s on the "map" to see whether they are friendly or hostile
 * and kicks off some notifications and stuff...
 *
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
        System.out.println("Scan iteration at radar with position (" + radar.getPosition().getX() + ", " + radar.getPosition().getY() + ")");

        for(Plane plane : PlaneCoordinator.getPlanes()){
        	if(RadarMath.isPlaneInTheRadar(plane, radar)){
        		if(plane.getType() == Plane.Type.HOSTILE){
        			System.out.println("Yakaladım looo");
        		}
        	}
        }
    }
}