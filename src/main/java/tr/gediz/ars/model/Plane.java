package tr.gediz.ars.model;

import javafx.scene.paint.Color;

/**
 * A model for a plane.
 */
public class Plane {
    public static final String[] MODELS = {
            "F-16",
            "F-14 Tomcat",
            "F-22",
            "F-35",
            "F-104",
            "F-5",
            "Mig-21",
            "Mig-29",
            "SU-35",
            "SU-29"
    };

    private String model;
    private int speed;
    private Type type = Type.HOSTILE;
    private Position position = new Position();
    private int pathType;
    private int yAnchor;
    private int pathExtensionConstant;

    private OnCaughtOnRadarListener listener;

    public Plane() {
        /* empty */
    }

    public Plane(String model, int speed, Type type, int pathType, int yAnchor) {
        this.model = model;
        this.speed = speed;
        this.type = type;
        this.pathType = pathType;
        this.yAnchor = yAnchor;
    }

    public Plane(String model, int speed, Type type, int pathType, int yAnchor, int pathExtensionConstant) {
        this(model, speed, type, pathType, yAnchor);
        this.pathExtensionConstant = pathExtensionConstant;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getPathType() {
        return pathType;
    }

    public void setPathType(int pathType) {
        this.pathType = pathType;
    }

    public int getYAnchor() {
        return yAnchor;
    }

    public void setYAnchor(int yAnchor) {
        this.yAnchor = yAnchor;
    }

    public int getPathExtensionConstant() {
        return pathExtensionConstant;
    }

    public void setPathExtensionConstant(int pathExtensionConstant) {
        this.pathExtensionConstant = pathExtensionConstant;
    }

    public void setListener(OnCaughtOnRadarListener listener) {
        this.listener = listener;
    }

    public OnCaughtOnRadarListener getListener() {
        return listener;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "model='" + model + '\'' +
                ", speed=" + speed +
                ", type=" + type.toString() +
                '}';
    }

    public enum Type {
        FRIENDLY("friendly", "#00E676"), HOSTILE("hostile", "#FF1744"), UNKNOWN("unknown", "#455A64"), INTERCEPTOR("interceptor", "#455A64");

        private String value;
        private Color indicatorColor;

        Type(String value, String indicatorColorHex) {
            this.value = value;
            this.indicatorColor = Color.web(indicatorColorHex);
        }

        public Color getIndicatorColor() {
            return indicatorColor;
        }

        @Override
        public String toString() {
            return value;
        }

        public Type fromString(String type) {
            switch (type) {
                case "friendly":
                    return FRIENDLY;
                case "hostile":
                    return HOSTILE;
                case "interceptor":
                    return INTERCEPTOR;
                default:
                    return UNKNOWN;
            }
        }
    }

    public interface OnCaughtOnRadarListener {
        void onCaughtOnRadar(Radar radar);
    }
}
