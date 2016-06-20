package tr.gediz.ars.model;

/**
 * A position on the 2D coordinate grid.
 */
public class Position {
    private int x;
    private int y;

    public Position() {
        /* empty */
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
