package tr.gediz.ars.logic;

import tr.gediz.ars.model.Plane;

/**
 * A class for mathematical logic behind randomized {@link Plane} movement.
 */
public class PlaneMath {

    /**
     * Enes gel buraya ve yaz sunu!
     * (TL: Enes come here and write this!)
     *
     * [le glorious javadoc by Enes Recep The Magnificent]
     *
     * @param xValue
     * @param pathNumber
     * @param yAddition
     * @param pathExtensionConstant
     * @return
     */
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
