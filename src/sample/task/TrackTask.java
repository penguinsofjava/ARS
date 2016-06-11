package sample.task;

import sample.model.Radar;
import sample.model.Missile;
import sample.model.Plane;
import sample.model.Position;

public class TrackTask {

    public static void trackPlaneWithMissile(Plane plane,Radar radar){
        Missile missile = new Missile(plane.getSpeed()*10,new Position(radar.getPosition().getX(),radar.getPosition().getY()));
        int distance;
        do{
            distance=calculateDistance(missile,plane);
            if(missile.getPosition().getX() < plane.getPosition().getX()){
                missile.getPosition().setX(missile.getPosition().getX()+plane.getSpeed()*2);
            }else{
                missile.getPosition().setX(missile.getPosition().getX()-plane.getSpeed()*2);
            }

            if(missile.getPosition().getY()<plane.getPosition().getY()){
                missile.getPosition().setY(missile.getPosition().getY()+plane.getSpeed()*2);
            }else{
                missile.getPosition().setY(missile.getPosition().getY()-plane.getSpeed()*2);
            }
        }while(distance > 50);

        if(distance < 50 ){
            missile.getPosition().setX(plane.getPosition().getX());
            missile.getPosition().setY(plane.getPosition().getY());
        }
    }



    public static int calculateDistance(Missile missile,Plane plane){
        int distance = (int) Math.sqrt((missile.getPosition().getX()-plane.getPosition().getX())*(missile.getPosition().getX()-plane.getPosition().getX())+missile.getPosition().getY()-plane.getPosition().getY()*missile.getPosition().getY()-plane.getPosition().getY());
        return distance;
    }
}
