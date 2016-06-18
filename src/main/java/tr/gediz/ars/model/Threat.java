package tr.gediz.ars.model;

public class Threat {
    private Radar founder;
    private Plane target;

    public Threat(Radar founder, Plane target) {
        this.founder = founder;
        this.target = target;
    }

    public Radar getRadar() {
        return founder;
    }

    public Plane getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return target.equals(((Threat) o).target);
    }
}
