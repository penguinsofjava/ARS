package sample.logic;

import java.util.Random;

public class PlaneMath {

    /**
     * This function generates Random planes.
     * There are 4 types of path for every plane.
     * Function generates random double number between 36-42 which are coordinates of TURKEY.
     * General formula is Y = Given X path + random double number.
     */
    public void randomPathGenerator() {
        Random rand = new Random();
        double basePointY;
        double basePointX = 24;
        double randomAddition = rand.nextDouble() * 7 + 36;

        int pathSelector = rand.nextInt(3) + 1;

        if (pathSelector == 1) {
            basePointY = (basePointX * basePointX) + randomAddition;
        } else if (pathSelector == 2) {
            basePointY = Math.sin(basePointX) + randomAddition;
        } else if (pathSelector == 3) {
            basePointY = Math.abs(basePointX) + randomAddition;
        } else if (pathSelector == 4) {
            basePointY = rand.nextDouble() * 7 + 36;
        }
    }
}
