package sample.coordinator;

import sample.Main;
import sample.model.Plane;
import sample.model.Position;
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
                addRandomPlane();
            }
        }, 0, interval);
    }

    public static void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public static void addRandomPlane() {
        PlaneCoordinator.addPlane(generatePlane());
    }

    public static Plane generatePlane() {
        /* Random model */
        int randomIndex = StaticRandom.get().nextInt(Plane.MODELS.length);
        String model = Plane.MODELS[randomIndex];

        /* Random speed */
        int speed = StaticRandom.get().nextInt(5) + 1;

        /* Random type */
        int typeIndex = StaticRandom.get().nextInt(3);
        Plane.Type type = Plane.Type.UNKNOWN;
        if (typeIndex == 0) {
            type = Plane.Type.FRIENDLY;
        } else if (typeIndex == 1) {
            type = Plane.Type.HOSTILE;
        }

        /* Random path type */
        int pathType = StaticRandom.get().nextInt(4) + 1;
        System.out.println("Plane type: " + pathType);

        int pathExtensionConstant = 1000;
        if (pathType == 1) {
             pathExtensionConstant = StaticRandom.get().nextInt(200) + 1500;
        }

        /* Random y anchor */
        int yAnchor = StaticRandom.get().nextInt(Main.getController().getMapHeight() + 1);

        return new Plane(model, speed, type, pathType, yAnchor, pathExtensionConstant);
    }

    public static Plane generateInterceptor(Plane target) {
        /* Speed */
        int speed = target.getSpeed() + 1;

        /* Type */
        Plane.Type type = Plane.Type.INTERCEPTOR;

        return new Plane(Plane.MODELS[0], speed, type, 0, 0, 0);
    }
}
