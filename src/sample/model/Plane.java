package sample.model;

import java.util.Arrays;

public class Plane {
    //Instance variables
    private String name;
    private int speed;
    private Position[] path;
    private Position position;

    //CONSTRUCTOR
    public Plane(String name, int speed, Position[] path, Position position) {
        this.name = name;
        this.speed = speed;
        this.path = path;
        this.position = position;
    }

    //SETTERS AND GETTERS
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

        //toString
    @Override
    public String toString() {
        return "Plane{" +
                "name='" + name + '\'' +
                ", speed=" + speed +
                ", path=" + Arrays.toString(path) +
                ", position=" + position +
                '}';
    }
}
