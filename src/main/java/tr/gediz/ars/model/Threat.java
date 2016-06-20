package tr.gediz.ars.model;

import javax.swing.text.html.ListView;

/**
 * A container class for storing a {@link Radar} and a {@link Plane} object.
 *
 * We use this class for keeping threatening planes and the radars that detected them together so that we can display
 * them in a {@link ListView}.
 */
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
