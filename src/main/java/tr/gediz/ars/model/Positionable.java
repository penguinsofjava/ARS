package tr.gediz.ars.model;

public interface Positionable {
    void setPosition(Position position);
    Position getPosition();
    void moveInX(int by);
    void moveInY(int by);
}
