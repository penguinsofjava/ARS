package sample.task;

import sample.model.Radar;
import sample.model.Missile;
import sample.model.Plane;
import sample.model.Position;

import java.util.TimerTask;

public class TrackTask extends TimerTask{

    private Radar radar;
    private Plane plane;

    public TrackTask(Radar radar, Plane plane) {
        this.radar = radar;
        this.plane = plane;
    }

    private void trackPlaneWithMissile(Plane plane, Radar radar){
        Missile missile = new Missile(plane.getSpeed()*10,new Position(radar.getPosition().getX(),radar.getPosition().getY()));
        int distance;
        do{
            distance=calculateDistance(missile,plane);
            if(missile.getPosition().getX() < plane.getPosition().getX()){
                missile.getPosition().setX(missile.getPosition().getX()+missile.getSpeed());
            }else{
                missile.getPosition().setX(missile.getPosition().getX()-missile.getSpeed());
            }

            if(missile.getPosition().getY()<plane.getPosition().getY()){
                missile.getPosition().setY(missile.getPosition().getY()+missile.getSpeed());
            }else{
                missile.getPosition().setY(missile.getPosition().getY()-missile.getSpeed());
            }
        }while(distance > 50);

        if(distance < 50 ){
            missile.getPosition().setX(plane.getPosition().getX());
            missile.getPosition().setY(plane.getPosition().getY());
        }


    }

    private int calculateDistance(Missile missile,Plane plane){
        int distance = (int) Math.sqrt((missile.getPosition().getX()-plane.getPosition().getX())*(missile.getPosition().getX()-plane.getPosition().getX())+missile.getPosition().getY()-plane.getPosition().getY()*missile.getPosition().getY()-plane.getPosition().getY());
        return distance;
    }

    @Override
    public void run() {
        trackPlaneWithMissile(plane, radar);
    }
}
