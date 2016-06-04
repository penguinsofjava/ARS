package sample.model;


import java.util.Arrays;

public class Plane {
    private String model;
    private int speed;
    private Type type = Type.HOSTILE;
    private Position[] path;
    private Position position = new Position();

    public Plane() {
        /* empty */
    }

    public Plane(String model, int speed, Position[] path) {
        this.model = model;
        this.speed = speed;
        this.path = path;
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

    public Position[] getPath() {
        return path.length != 0 ? path : null;
    }

    public void setPath(Position[] path) {
        this.path = path;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "model='" + model + '\'' +
                ", speed=" + speed +
                ", path=" + Arrays.toString(path) +
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
