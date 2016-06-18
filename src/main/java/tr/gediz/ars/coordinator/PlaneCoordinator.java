package tr.gediz.ars.coordinator;

import com.google.common.eventbus.Subscribe;
import tr.gediz.ars.bus.Bus;
import tr.gediz.ars.bus.event.PlaneAddedEvent;
import tr.gediz.ars.bus.event.PlaneInterceptedEvent;
import tr.gediz.ars.bus.event.PlaneRemovedEvent;
import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.model.Threat;

import java.util.ArrayList;

import static tr.gediz.ars.Main.getController;

public class PlaneCoordinator {
    private static ArrayList<Plane> planes = new ArrayList<>();
    private static ArrayList<Plane> trackedPlanes = new ArrayList<>();

    static {
        Bus.get().register(new PlaneCoordinator());
    }

    private PlaneCoordinator() {
        /* empty */
    }

    public static ArrayList<Plane> getPlanes() {
        return planes;
    }

    public static void addPlane(Plane plane) {
        planes.add(plane);
        Bus.get().post(new PlaneAddedEvent(plane));
    }

    public static void removePlane(Plane plane) {
        planes.remove(plane);
        Bus.get().post(new PlaneRemovedEvent(plane));
    }

    public static void interceptPlane(Plane target, Radar radar) {
        if (!trackedPlanes.contains(target)) {
            trackedPlanes.add(target);
            addPlane(PlaneFactory.generateInterceptor(target, radar));
        }
    }

    @Subscribe
    public void onInterceptionByPlane(PlaneInterceptedEvent event) {
        if (trackedPlanes.contains(event.getTarget())) {
            trackedPlanes.remove(event.getTarget());
        }
        PlaneCoordinator.removePlane(event.getTarget());
        getController().indicateTakedown(event.getTarget().getPosition());
        getController().removeThreat(new Threat(event.getRadar(), event.getTarget()));
    }
}
