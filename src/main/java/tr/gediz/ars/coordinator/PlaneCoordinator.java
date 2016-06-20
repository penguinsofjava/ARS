package tr.gediz.ars.coordinator;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import tr.gediz.ars.bus.Bus;
import tr.gediz.ars.bus.event.PlaneAddedEvent;
import tr.gediz.ars.bus.event.PlaneInterceptedEvent;
import tr.gediz.ars.bus.event.PlaneRemovedEvent;
import tr.gediz.ars.controller.MainController;
import tr.gediz.ars.factory.PlaneFactory;
import tr.gediz.ars.model.InterceptorPlane;
import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.model.Threat;

import java.util.ArrayList;

/**
 * A static class that manages {@link Plane}s altogether. It it responsible for storing them and for their addition and
 * removal. It also initiates interceptions and takedowns. Handling of interception and takedown results is also done here.
 */
public class PlaneCoordinator {
    private static ArrayList<Plane> planes = new ArrayList<>();
    private static ArrayList<Plane> trackedPlanes = new ArrayList<>();

    static {
        Bus.get().register(new PlaneCoordinator());
    }

    /**
     * Empty constructor to prevent instantiation.
     */
    private PlaneCoordinator() {}

    /**
     * @return Planes currently in the "system" that are being shown on the map.
     */
    public static ArrayList<Plane> getPlanes() {
        return planes;
    }

    /**
     * Adds a given {@link Plane} to the storage and posts a {@link PlaneAddedEvent}.
     *
     * @param plane {@link Plane} to be added.
     */
    public static void addPlane(Plane plane) {
        planes.add(plane);
        Bus.get().post(new PlaneAddedEvent(plane));
    }

    /**
     * Removes a given {@link Plane} from the storage and posts a {@link PlaneRemovedEvent}.
     *
     * @param plane {@link Plane} to be removed.
     */
    public static void removePlane(Plane plane) {
        planes.remove(plane);
        Bus.get().post(new PlaneRemovedEvent(plane));
    }

    /**
     * Intercepts a given {@link Plane}.
     *
     * This is done by the generation of a {@link InterceptorPlane} through {@link PlaneFactory}.
     *
     * @param target {@link Plane} to be intercepted.
     * @param radar {@link Radar} that detected the target.
     */
    public static void interceptPlane(Plane target, Radar radar) {
        if (!trackedPlanes.contains(target)) {
            trackedPlanes.add(target);
            addPlane(PlaneFactory.generateInterceptor(target, radar));
        }
    }

    /**
     * Shoots a given {@link Plane} down.
     *
     * @param radar {@link Radar} that detected the target.
     * @param target {@link Plane} to be shot.
     */
    public static void shootdownPlane(Radar radar, Plane target) {
        if (!trackedPlanes.contains(target)) {
            trackedPlanes.add(target);
            MainController.getInstance().drawMissile(radar, target);
        }
    }

    /**
     * An {@link EventBus} subscriber method that listens for {@link PlaneInterceptedEvent}.
     *
     * @param event A {@link PlaneInterceptedEvent} that is published.
     */
    @Subscribe
    public void onPlaneIntercepted(PlaneInterceptedEvent event) {
        trackedPlanes.remove(event.getTarget());
        PlaneCoordinator.removePlane(event.getTarget());
        MainController.getInstance().indicateTakedown(event.getTarget().getPosition());
        MainController.getInstance().removeThreat(new Threat(event.getRadar(), event.getTarget()));
    }
}
