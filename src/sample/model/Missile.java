package sample.model;


public class Missile {
    private int speed;
    private Position position;

    public Missile(int speed, Position position) {
        this.speed = speed;
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
