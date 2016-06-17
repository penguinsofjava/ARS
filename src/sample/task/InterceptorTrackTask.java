package sample.task;

import sample.coordinator.PlaneCoordinator;
import sample.model.InterceptorPlane;
import sample.model.Plane;
import sample.view.PlaneView;

import java.util.TimerTask;

public class InterceptorTrackTask extends TimerTask {
    private Plane target;
    private PlaneView interceptor;

    public static InterceptorTrackTask with(Plane plane, PlaneView interceptor) {
        return new InterceptorTrackTask(plane, interceptor);
    }

    private InterceptorTrackTask(Plane target, PlaneView interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }
    boolean track = true;

    private void trackWithInterceptor() {

        if(track){
            if (calculateDistance() > 10) {
                int interceptorX = interceptor.getPosition().getX();
                int interceptorY = interceptor.getPosition().getY();
                int speed = interceptor.getPlane().getSpeed();
                interceptor.setX(interceptorX < target.getPosition().getX() ? interceptorX + speed : interceptorX - speed);
                interceptor.setY(interceptorY < target.getPosition().getY() ? interceptorY + speed : interceptorY - speed);
            } else {
                PlaneCoordinator.notifyPlaneTakenDown(target);
                PlaneCoordinator.removePlane(target);
                track = false;
            }
        }else{
            returnToBase();
        }

    }

    private void returnToBase(){
        if(calculateDistanceToAirBase()>5){
            int baseX = ((InterceptorPlane) interceptor.getPlane()).getAirbase().getPosition().getX();
            int baseY = ((InterceptorPlane) interceptor.getPlane()).getAirbase().getPosition().getY();
            int speed = interceptor.getPlane().getSpeed();
            int interceptorX = interceptor.getPosition().getX();
            int interceptorY = interceptor.getPosition().getY();

            if(interceptorX > baseX){
                interceptor.setX(interceptorX - speed);
            }else{
                interceptor.setX(interceptorX + speed);
            }

            if(interceptorY > baseY){
                interceptor.setY(interceptorY - speed);
            }else{
                interceptor.setY(interceptorY + speed);
            }
        }else{
            PlaneCoordinator.removePlane(interceptor.getPlane());
        }



    }

    private int calculateDistance() {
        int interceptorX = interceptor.getPosition().getX();
        int interceptorY = interceptor.getPosition().getY();
        int targetX = target.getPosition().getX();
        int targetY = target.getPosition().getY();
        return (int) Math.sqrt(Math.pow(interceptorX - targetX, 2) + Math.pow(interceptorY - targetY, 2));
    }

    private int calculateDistanceToAirBase(){
        int interceptorX = interceptor.getPosition().getX();
        int interceptorY = interceptor.getPosition().getY();
        int airbaseX = ((InterceptorPlane)interceptor.getPlane()).getAirbase().getPosition().getX();
        int airBaseY= ((InterceptorPlane)interceptor.getPlane()).getAirbase().getPosition().getY();

        return (int) Math.sqrt(Math.pow(interceptorX-airbaseX,2) + Math.pow(interceptorY-airBaseY,2));

    }

    @Override
    public void run() {
        trackWithInterceptor();
    }
}
