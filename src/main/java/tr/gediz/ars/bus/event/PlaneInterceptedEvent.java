package tr.gediz.ars.bus.event;

import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Radar;

/**
 * An EventBus event that is published when a plane has been intercepted (i.e., taken down).
 */
public class PlaneInterceptedEvent {
    private Radar radar;
    private Plane target;

    public PlaneInterceptedEvent(Radar radar, Plane target) {
        this.radar = radar;
        this.target = target;
    }

    /**
     * @return {@link Radar} that found the {@link Plane} that was intercepted.
     */
    public Radar getRadar() {
        return radar;
    }

    /**
     * @return {@link Plane} that was intercepted.
     */
    public Plane getTarget() {
        return target;
    }
}
