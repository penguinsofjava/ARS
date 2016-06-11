package sample.model;

public class Radar {
    private int id;
    private int scanAngle = 40; // default (degrees)
    private int radius = 50; // default (pixels)
    private int scanInterval = 1000; // default (milliseconds)
    private int scanAngleAlpha;
    private Position position;

    private ChangeListener listener;

    public Radar() {
        /* Empty - Not sure if we'll ever need this... Keeping it for now... */
    }

    public Radar(int id, Integer scanAngle, Integer radius, Integer scanInterval, Position position) {
        this.id = id;
        if (scanAngle != null) this.scanAngle = scanAngle;
        if (radius != null) this.radius = radius;
        if (scanInterval != null) this.scanInterval = scanInterval;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScanAngle() {
        return scanAngle;
    }

    public void setScanAngle(int scanAngle) {
        this.scanAngle = scanAngle;
        notifyChange();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        notifyChange();
    }

    public int getWidth() {
        return 2 * radius;
    }

    public int getHeight() {
        return getWidth();
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
        notifyChange();
    }

    public int getScanAngleAlpha() {
        return scanAngleAlpha;
    }

    public void setScanAngleAlpha(int scanAngleAlpha) {
        this.scanAngleAlpha = scanAngleAlpha;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position = new Position(x, y);
    }

    public void setPosition(Position position) {
        setPosition(position);
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    private void notifyChange() {
        if (listener != null) {
            listener.onChange();
        }
    }

    public interface ChangeListener {
        void onChange();
    }
}
