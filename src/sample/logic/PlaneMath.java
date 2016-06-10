package sample.logic;

public class PlaneMath {

    public static int getYCoordinate(int xValue, int pathNumber, int yAddition) {
        int yValue = 0;
        if (pathNumber == 1) {
            yValue = (xValue * xValue) + yAddition;
        } else if (pathNumber == 2) {
            yValue = (int) (Math.sin(xValue) + yAddition);
        } else if (pathNumber == 3) {
            yValue = Math.abs(xValue) + yAddition;
        } else if (pathNumber == 4) {
            yValue = yAddition;
        }
        return yValue;
    }
}
