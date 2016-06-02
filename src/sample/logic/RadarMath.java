package sample.logic;

import java.util.Random;

import sample.model.Plane;
import sample.model.Radar;

public class RadarMath {
	
	/*
	 * The function controls the 2 statement. If the coordinates of the plane satisfy the circle equation
	 * AND corrects the angle equation, it means that the plane is in the radar.
	 * 
	 */
	public static boolean isPlaneInTheRadar(Plane plane, Radar radar){
		
		if(isPlaneInTheCircle(plane, radar)  &&  isPlaneInTheAngle(plane, radar)){
			return true;
		}
		else
		{
			return false;
		}
		
	}
	/*
	 * This function controls whether coordinates of plane satisfy the circle equation or not.
	 * If they satisfy, the function returns TRUE, else FALSE.
	 */
	public static boolean isPlaneInTheCircle(Plane plane, Radar radar){
		
		//General circle equation. r^2 = (x^2+y^2)
		double xSideOfTheEquation = (plane.getPosition().getX() - radar.getPosition().getX())*(plane.getPosition().getX() - radar.getPosition().getX());
		double ySideOFTheEquation = (plane.getPosition().getY() - radar.getPosition().getY())*(plane.getPosition().getY() - radar.getPosition().getY());
		double radarRadius = radar.getRadius();
		
		return xSideOfTheEquation + ySideOFTheEquation <= radarRadius;
	}
	
	
	/*
	 * This function checks if the points of the plane in the given angle or not.
	 * If so returns TRUE, else FALSE.
	 */
	public static boolean isPlaneInTheAngle(Plane plane, Radar radar){
		
		double planeX = plane.getPosition().getX();
		double planeY = plane.getPosition().getY();
		
		double planeTheta = Math.atan(Math.toRadians(planeX / planeY));
		
		double radarAlpha = radar.getScanAngleAlpha();
		double radarBeta = radar.getScanAngle();
		
		return planeTheta <= (radarAlpha + radarBeta) && planeTheta >= radarAlpha;
	}
	
	
	/*
	 * This function generates Random planes.
	 * There are 4 types of path for every plane.
	 * Function generates random double number between 36-42 which are coordinates of TURKEY.
	 * General formula is Y = Given X path + random double number.
	 * 
	 */
	public void randomPathGenerator(){
		
		Random rand = new Random();
		double basePointY;
		double basePointX = 24;
		double randomAddition = rand.nextDouble() * 7 + 36;
		
		int pathSelector = rand.nextInt(3) + 1;
		
		if(pathSelector == 1){
			basePointY = (basePointX * basePointX) + randomAddition ;
		}
		else if(pathSelector == 2){
			basePointY = Math.sin(basePointX) + randomAddition;
		}
		else if(pathSelector == 3){
			basePointY = Math.abs(basePointX) + randomAddition;
		}
		else if(pathSelector == 4){
			basePointY = rand.nextDouble() * 7 + 36;
		}
		
	}


}