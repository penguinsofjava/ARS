package sample.view;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.Main;
import sample.coordinator.PlaneCoordinator;
import sample.model.Plane;
import sample.model.Position;
import sample.model.Positionable;
import sample.model.Radar;
import sample.task.FlyTask;

import java.util.Timer;
import java.util.TimerTask;

public class PlaneView extends Pane implements Positionable, Plane.OnCaughtOnRadarListener {
    private static final int WIDTH = 10; // px
    private static final int HEIGHT = 10; // px

    private Plane plane;

   protected Timer flyTimer;

    private FadeTransition radarShowTransition;

    public PlaneView(Plane plane) {
        setPlane(plane);
    }

    public void setPlane(Plane plane) {
        this.plane = plane;

        setWidth(WIDTH);
        setHeight(HEIGHT);

        setPosition(new Position(0, plane.getYAnchor()));

        plane.setListener(this);

//        setOpacity(0); // TODO: Uncomment

        draw();
    }

    public Plane getPlane() {
        return plane;
    }

    public void startFlying() {
        if (flyTimer == null) {
            flyTimer = new Timer();
        }
        flyTimer.schedule(FlyTask.with(this), 0, 100);
    }

    public void stopFlying() {
        if (flyTimer != null) {
            flyTimer.cancel();
            flyTimer = null;
        }
    }

    @Override
    public void onCaughtOnRadar(Radar radar) {
//        animateRadarShowUp(); // TODO: Uncomment
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
                radarShowTransition.play();
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
