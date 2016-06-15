package sample.task;

import sample.coordinator.PlaneCoordinator;
import sample.model.Plane;
import sample.model.Radar;
import sample.view.InterceptorPlaneView;

import java.util.TimerTask;

public class InterceptorTrackTask extends TimerTask {
    private Plane target;
    private InterceptorPlaneView interceptor;

    public static InterceptorTrackTask with(Plane plane, InterceptorPlaneView interceptor) {
        return new InterceptorTrackTask(plane, interceptor);
    }

    private InterceptorTrackTask(Plane target, InterceptorPlaneView interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    private void trackWithInterceptor() {
        int distance = calculateDistance();

        System.out.println("Distance " + distance);
        if (distance > 10) {
            System.out.println("Boo!");
            if (interceptor.getPosition().getX() < target.getPosition().getX()) {
                interceptor.setX(target.getPosition().getX() + interceptor.getPlane().getSpeed());
            } else {
                interceptor.setX(target.getPosition().getX() - interceptor.getPlane().getSpeed());
            }

            if (interceptor.getPosition().getY() < target.getPosition().getY()) {
                interceptor.setY(target.getPosition().getY() + interceptor.getPlane().getSpeed());
            } else {
                interceptor.setY(target.getPosition().getY() - interceptor.getPlane().getSpeed());
            }
        } else {
            // TODO: Stop
            PlaneCoordinator.removePlane(target);
            System.out.println("Boom!");
            this.cancel();
        }
    }

    private int calculateDistance() {
        return (int) Math.sqrt(
                (interceptor.getPosition().getX() - target.getPosition().getX())
                        * (interceptor.getPosition().getX() - target.getPosition().getX())
                        + (interceptor.getPosition().getY() - target.getPosition().getY())
                        * (interceptor.getPosition().getY() - target.getPosition().getY())
        );
    }

    @Override
    public void run() {
        trackWithInterceptor();
    }
}
