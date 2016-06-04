package sample.logic;

import sample.model.Plane;
import sample.model.Radar;

public class RadarMath {

    /**
     * The function controls the 2 statement. If the coordinates of the plane satisfy the circle equation
     * AND corrects the angle equation, it means that the plane is in the radar.
     */
    public static boolean isPlaneInTheRadar(Plane plane, Radar radar) {
        return isPlaneInTheCircle(plane, radar) && isPlaneInTheAngle(plane, radar);
    }

    /**
     * This function controls whether coordinates of plane satisfy the circle equation or not.
     * If they satisfy, the function returns TRUE, else FALSE.
     */
    public static boolean isPlaneInTheCircle(Plane plane, Radar radar) {
        //General circle equation. r^2 = (x^2+y^2)
        double xSideOfTheEquation = (plane.getPosition().getX() - radar.getPosition().getX()) * (plane.getPosition().getX() - radar.getPosition().getX());
        double ySideOFTheEquation = (plane.getPosition().getY() - radar.getPosition().getY()) * (plane.getPosition().getY() - radar.getPosition().getY());
        double radarRadius = radar.getRadius();

        return xSideOfTheEquation + ySideOFTheEquation <= Math.pow(radarRadius, 2);
    }

    /**
     * This function checks if the points of the plane in the given angle or not.
     * If so returns TRUE, else FALSE.
     */
    public static boolean isPlaneInTheAngle(Plane plane, Radar radar) {
        double planeX = plane.getPosition().getX() - radar.getPosition().getX();
        double planeY = -1 * plane.getPosition().getY() + radar.getPosition().getY();

        double planeTheta = Math.toRadians(160) + Math.atan(planeY / planeX);
        double radarAlpha = Math.toRadians(radar.getScanAngleAlpha());
        double radarBeta = Math.toRadians(radar.getScanAngle());

        return planeTheta <= (radarAlpha + radarBeta) && planeTheta >= radarAlpha;
    }
}