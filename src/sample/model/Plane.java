package sample.model;

public class Plane {
    private String model;
    private int speed;
    private Type type = Type.HOSTILE;
    private Position position = new Position();
    private int pathType;
    private int yAnchor;

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

    public static String[] getModels() {
        return models;
    }

    public static void setModels(String[] models) {
        Plane.models = models;
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
        FRIENDLY("friendly"), HOSTILE("hostile"), UNKNOWN("unknown");

        private String value;

        Type(String value) {
            this.value = value;
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
                default:
                    return UNKNOWN;
            }
        }
    }

    public static String[] models = {
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
}
