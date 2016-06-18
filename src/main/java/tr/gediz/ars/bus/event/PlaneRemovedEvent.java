package tr.gediz.ars.bus.event;

import tr.gediz.ars.model.Plane;

public class PlaneRemovedEvent {
    private Plane plane;

    public PlaneRemovedEvent(Plane plane) {
        this.plane = plane;
    }

    public Plane getPlane() {
        return plane;
    }
}
