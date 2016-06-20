package tr.gediz.ars.model;

/**
 * A model class for an interceptor plane. These are planes that are launched when a threatening plane is to be taken down.
 */
public class InterceptorPlane extends Plane {
    /**
     * {@link Plane} that this interceptor plane is to take down.
     */
    private Plane target;
    /**
     * {@link Radar} that this interceptor plane is launched from and goes back to.
     */
    private Radar airbase;

    public InterceptorPlane(int speed, Plane target, Radar airbase) {
        super("F-16", speed, Type.INTERCEPTOR, 0, 0);
        this.target = target;
        this.airbase = airbase;
    }

    public Plane getTarget() {
        return target;
    }

    public void setTarget(Plane target) {
        this.target = target;
    }

    public Radar getAirbase() {
        return airbase;
    }

    public void setAirbase(Radar airbase) {
        this.airbase = airbase;
    }
}
