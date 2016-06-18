package tr.gediz.ars.bus.event;

import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Radar;

public class PlaneInterceptedEvent {
    private Radar radar;
    private Plane interceptor;
    private Plane target;

    public PlaneInterceptedEvent(Radar radar, Plane interceptor, Plane target) {
        this.radar = radar;
        this.interceptor = interceptor;
        this.target = target;
    }

    public Radar getRadar() {
        return radar;
    }

    public Plane getInterceptor() {
        return interceptor;
    }

    public Plane getTarget() {
        return target;
    }
}
