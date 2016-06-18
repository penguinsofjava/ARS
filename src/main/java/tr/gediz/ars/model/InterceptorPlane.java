package tr.gediz.ars.model;

public class InterceptorPlane extends Plane {
    private Plane target;
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
