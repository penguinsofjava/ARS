package sample.model;

import java.util.Arrays;

public class Plane {
    private String name;
    private int speed;
    private Type type;
    private Position[] path;
    private Position position;

    public Plane(String name, int speed, Position[] path, Position position) {
        this.name = name;
        this.speed = speed;
        this.path = path;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Position[] getPath() {
        return path;
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
                "name='" + name + '\'' +
                ", speed=" + speed +
                ", path=" + Arrays.toString(path) +
                ", position=" + position +
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
}
