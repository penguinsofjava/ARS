package sample.task;

import sample.coordinator.PlaneCoordinator;
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

    private void trackWithInterceptor() {
        if (calculateDistance() > 10) {
            int interceptorX = interceptor.getPosition().getX();
            int interceptorY = interceptor.getPosition().getY();
            int speed = interceptor.getPlane().getSpeed();
            interceptor.setX(interceptorX < target.getPosition().getX() ? interceptorX + speed : interceptorX - speed);
            interceptor.setY(interceptorY < target.getPosition().getY() ? interceptorY + speed : interceptorY - speed);
        } else {
            PlaneCoordinator.notifyPlaneTakenDown(target);
            PlaneCoordinator.removePlane(target);
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

    @Override
    public void run() {
        trackWithInterceptor();
    }
}
