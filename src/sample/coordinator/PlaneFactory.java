package sample.coordinator;

import sample.Controller;
import sample.model.Plane;
import sample.util.StaticRandom;

import java.util.Timer;
import java.util.TimerTask;

public class PlaneFactory {
    private static Timer timer;

    public static void start() {
        start(5000);
    }

    public static void start(int interval) {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                addPlane();
            }
        }, 0, interval);
    }

    public static void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private static void addPlane() {
        /* Random model */
        int randomIndex = StaticRandom.get().nextInt(Plane.models.length);
        String model = Plane.models[randomIndex];

        /* Random speed */
        int speed = StaticRandom.get().nextInt(4);

        /* Random type */
        int typeIndex = StaticRandom.get().nextInt(3);
        Plane.Type type = Plane.Type.UNKNOWN;
        if (typeIndex == 0) {
            type = Plane.Type.FRIENDLY;
        } else if (typeIndex == 1) {
            type = Plane.Type.HOSTILE;
        }

        /* Random path type */
        int pathType = StaticRandom.get().nextInt(5);

        /* Random y anchor */
        int yAnchor = StaticRandom.get().nextInt(Controller.MAP_HEIGHT + 1);

        Plane plane = new Plane(model, speed, type, pathType, yAnchor);

        PlaneCoordinator.addPlane(plane);
    }
}
