package sample.model;


import java.util.Arrays;

public class Plane {
    private String name;
    private int speed;
    private Type type = Type.HOSTILE;
    private Position[] path;
    private Position position = new Position();

    public Plane(String name, int speed, Position[] path) {
        this.name = name;
        this.speed = speed;
        this.path = path;
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
                "name='" + name + '\'' +
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
}
