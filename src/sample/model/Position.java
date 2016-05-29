package sample.model;

public interface Position {
    void setPosition(int x, int y);
    int[] getPosition();
    void moveInX(int by);
    void moveInY(int by);
    int getPositionX();
    int getPositionY();
}
