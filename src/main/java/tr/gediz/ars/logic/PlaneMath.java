package tr.gediz.ars.logic;

public class PlaneMath {

    public static int getYCoordinate(int xValue, int pathNumber, int yAddition, int pathExtensionConstant) {
        int yValue = 0;
        if (pathNumber == 1) {
            yValue = ((xValue * xValue) / pathExtensionConstant) + yAddition;
        } else if (pathNumber == 2) {
            yValue = (int) ((Math.sin(xValue) * 3) + yAddition);
        } else if (pathNumber == 3) {
            yValue = Math.abs(xValue) + yAddition;
        } else if (pathNumber == 4) {
            yValue = yAddition;
        }
        return yValue;
    }
}
