package tr.gediz.ars.factory;

import tr.gediz.ars.controller.MainController;
import tr.gediz.ars.coordinator.PlaneCoordinator;
import tr.gediz.ars.model.InterceptorPlane;
import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.util.StaticRandom;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A class that generates a {@link Plane} with randomized properties on each iteration of a {@link Timer}.
 */
public class PlaneFactory {
    private static Timer timer;

    public static void start() {
        start(5000);
    }

    /**
     * Start the {@link Timer} that generates the {@link Plane}s.
     *
     * @param interval Duration of a single iteration.
     */
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

    /**
     * Stops the {@link Timer} that generates the {@link Plane}s.
     */
    public static void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Adds a new randomized {@link Plane} through {@link PlaneCoordinator}.
     */
    public static void addRandomPlane() {
        PlaneCoordinator.addPlane(generatePlane());
    }

    /**
     * Generates and returns a randomized {@link Plane}.
     *
     * @return The generated {@link Plane}.
     */
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
        int yAnchor = StaticRandom.get().nextInt(MainController.getInstance().getMapHeight() + 1);

        return new Plane(model, speed, type, pathType, yAnchor, pathExtensionConstant);
    }

    /**
     * Generates an {@link InterceptorPlane} with given {@link Plane} and {@link Radar} instances.
     *
     * @param target {@link Plane} that this {@link InterceptorPlane} will target.
     * @param radar  {@link Radar} that will be the "airbase" for the {@link InterceptorPlane}.
     * @return The generated {@link InterceptorPlane}.
     */
    public static InterceptorPlane generateInterceptor(Plane target, Radar radar) {
        return new InterceptorPlane(target.getSpeed() + 1, target, radar);
    }
}
