package tr.gediz.ars.bus.event;

import tr.gediz.ars.model.Plane;

/**
 * An EventBus event that is published when a new plane has been added.
 */
public class PlaneAddedEvent {
    private Plane plane;

    public PlaneAddedEvent(Plane plane) {
        this.plane = plane;
    }

    public Plane getPlane() {
        return plane;
    }
}
