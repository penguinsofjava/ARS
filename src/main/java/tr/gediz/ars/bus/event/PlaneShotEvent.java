package tr.gediz.ars.bus.event;

import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Radar;

public class PlaneShotEvent {
    private Radar radar;
    private Plane target;

    public PlaneShotEvent(Radar radar, Plane target) {
        this.radar = radar;
        this.target = target;
    }

    public Radar getRadar() {
        return radar;
    }

    public Plane getTarget() {
        return target;
    }
}
