package sample.task;

import sample.model.InterceptorPlane;
import sample.model.Plane;
import sample.model.Position;
import sample.model.Radar;

import java.util.TimerTask;

public class InterceptorTrackTask extends TimerTask{


    private Plane plane;
    private Radar radar;

    public InterceptorTrackTask(Plane plane, Radar radar) {
        this.plane = plane;
        this.radar = radar;
    }

    private void trackWithInterceptor(Radar radar, Plane plane){
        InterceptorPlane interceptor = new InterceptorPlane(new Position(radar.getPosition().getX(),radar.getPosition().getY()),plane.getSpeed()*2);
        int distance=calculateDistance(interceptor,plane);
        do{
            if(distance > 50){
                if(interceptor.getPosition().getX() < plane.getPosition().getX()){
                    interceptor.getPosition().setX(plane.getPosition().getX() + interceptor.getSpeed());
                }else{
                    interceptor.getPosition().setX(plane.getPosition().getX() - interceptor.getSpeed());
                }

                if(interceptor.getPosition().getY() < plane.getPosition().getY()){
                    interceptor.getPosition().setY(plane.getPosition().getY() + interceptor.getSpeed());
                }else{
                    interceptor.getPosition().setY(plane.getPosition().getY() - interceptor.getSpeed());
                }
            }else{
                break;
            }
        }while(true);
    }

    private int calculateDistance(InterceptorPlane interceptor,Plane plane){
        int distance = (int) Math.sqrt((interceptor.getPosition().getX()-plane.getPosition().getX())*(interceptor.getPosition().getX()-plane.getPosition().getX())+interceptor.getPosition().getY()-plane.getPosition().getY()*interceptor.getPosition().getY()-plane.getPosition().getY());
        return distance;
    }




    @Override
    public void run() {
        trackWithInterceptor(radar,plane);
    }


}
