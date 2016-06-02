package sample.model;

public class Radar {
    private int id;
    private int scanAngle = 20; // default (degrees)
    private int radius = 50; // default (pixels)
    private int scanInterval = 1000; // default (milliseconds)
    private int scanAngleAlpha;
    private Position position;

    private ChangeListener listener;
    private Color color = Color.ACTIVE;

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    private void notifyChange() {
        if (listener != null) {
            listener.onChange();
        }
    }

    public enum Color {
        ACTIVE("#4CAF50", "#388E3C"), THREAT("#F44336", "#D32F2F"), INACTIVE("#757575", "#424242");

        private javafx.scene.paint.Color fill;
        private javafx.scene.paint.Color stroke;

        Color(String fillHex, String strokeHex) {
            fill = javafx.scene.paint.Color.web(fillHex, 0.35d);
            stroke = javafx.scene.paint.Color.web(strokeHex, 0.5d);
        }

        public javafx.scene.paint.Color getFill() {
            return fill;
        }

        public javafx.scene.paint.Color getStroke() {
            return stroke;
        }
    }

    public interface ChangeListener {
        void onChange();
    }
}
