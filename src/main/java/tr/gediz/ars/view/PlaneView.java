package tr.gediz.ars.view;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import tr.gediz.ars.Main;
import tr.gediz.ars.coordinator.PlaneCoordinator;
import tr.gediz.ars.model.*;
import tr.gediz.ars.task.FlyTask;
import tr.gediz.ars.task.InterceptorTrackTask;

import java.util.Timer;
import java.util.TimerTask;

public class PlaneView extends Pane implements Positionable, Plane.OnCaughtOnRadarListener {
    private static final int WIDTH = 8; // px
    private static final int HEIGHT = 8; // px

    private Plane plane;

    private Timer flyTimer;

    private FadeTransition radarShowTransition;

    /**
     * Defines whether the plane should be hidden on map until detected and slowly faded away after detection.
     */
    private boolean shouldAnimateVisibility;

    public PlaneView(Plane plane) {
        setPlane(plane);
    }

    public void setPlane(Plane plane) {
        this.plane = plane;

        setWidth(WIDTH);
        setHeight(HEIGHT);

        setPosition(new Position(0, plane.getYAnchor()));

        plane.setListener(this);

        if (plane.getType() == Plane.Type.HOSTILE || plane.getType() == Plane.Type.UNKNOWN) {
            shouldAnimateVisibility = true;
            setOpacity(0);
        }

        if (plane instanceof InterceptorPlane) {
            setPosition(((InterceptorPlane) plane).getAirbase().getPosition());
        }

        draw();
    }

    public Plane getPlane() {
        return plane;
    }

    public void startFlying() {
        if (flyTimer == null) {
            flyTimer = new Timer();
        }
        if (plane instanceof InterceptorPlane) {
            flyTimer.schedule(InterceptorTrackTask.with(((InterceptorPlane) plane).getTarget(), this), 0, 100);
        } else {
            flyTimer.schedule(FlyTask.with(this), 0, 100);
        }
    }

    public void stopFlying() {
        if (flyTimer != null) {
            flyTimer.cancel();
            flyTimer = null;
        }
    }

    /**
     * Toggles whether this plane is being tracked. A usage case of this is when the plane is of {@link Plane.Type#HOSTILE}
     * and is being tracked by an {@link Plane.Type#INTERCEPTOR} plane. Marking this as true causes the plane to never
     * "fade away" on the map.
     */
    public void setBeingTracked(boolean tracked) {
        shouldAnimateVisibility = !tracked;
        if (tracked) {
            setOpacity(1);
        }
    }

    @Override
    public void onCaughtOnRadar(Radar radar) {
        if (shouldAnimateVisibility) {
            animateRadarShowUp();
        }
    }

    private void animateRadarShowUp() {
        if (radarShowTransition == null) {
            radarShowTransition = new FadeTransition(Duration.millis(1000), this);
            radarShowTransition.setFromValue(1);
            radarShowTransition.setToValue(0);
            radarShowTransition.setInterpolator(Interpolator.LINEAR);
        }
        setOpacity(1);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (shouldAnimateVisibility) {
                    radarShowTransition.play();
                }
            }
        }, 200);
    }

    @Override
    public void setPosition(Position position) {
        setX(position.getX());
        setY(position.getY());
    }

    @Override
    public Position getPosition() {
        return new Position((int) getLayoutX(), (int) getLayoutY());
    }

    @Override
    public void moveInX(int by) {
        setX((int) getLayoutX() + by);
    }

    @Override
    public void moveInY(int by) {
        setY((int) getLayoutY() + by);
    }

    public void setX(int x) {
        setLayoutX(x);
        plane.getPosition().setX(x);
        notifyPositionChanged();
    }

    public void setY(int y) {
        setLayoutY(y);
        plane.getPosition().setY(y);
        notifyPositionChanged();
    }

    private void notifyPositionChanged() {
        if (getLayoutY() >= Main.getController().getMapHeight() - getHeight()
                || getLayoutX() >= Main.getController().getMapWidth() - getWidth()) {
            System.out.println("Plane out of map. Removing...");
            stopFlying();
            PlaneCoordinator.removePlane(plane);
        }
    }

    private void draw() {
        if (plane == null) return;

        Canvas canvas = new Canvas(PlaneView.WIDTH, PlaneView.HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(getPlane().getType().getIndicatorColor());
        graphicsContext.fillOval(0, 0, PlaneView.WIDTH, PlaneView.HEIGHT);
        getChildren().clear();
        getChildren().add(canvas);
    }
}
