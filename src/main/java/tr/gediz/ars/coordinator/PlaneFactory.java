package tr.gediz.ars.coordinator;

import tr.gediz.ars.Main;
import tr.gediz.ars.model.InterceptorPlane;
import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.util.StaticRandom;

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

    public static InterceptorPlane generateInterceptor(Plane target, Radar radar) {
        return new InterceptorPlane(target.getSpeed() + 1, target, radar);
    }
}
