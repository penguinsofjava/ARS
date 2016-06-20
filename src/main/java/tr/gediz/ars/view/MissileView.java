package tr.gediz.ars.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import tr.gediz.ars.controller.MainController;
import tr.gediz.ars.model.*;
import tr.gediz.ars.task.TrackTask;

import java.util.Timer;

/**
 * A subclass of {@link Pane} that we use to display missiles on screen. It's really just simple {@link Canvas} drawing of a
 * circle.
 */
public class MissileView extends Pane implements Positionable {
    public static final int SPEED = 6;

    private static final int WIDTH = 5; // px
    private static final int HEIGHT = 5; // px

    private Plane target;
    private Radar radar;

    private Timer flyTimer;

    public MissileView(Radar radar, Plane target) {
        this.radar = radar;
        this.target = target;

        setWidth(WIDTH);
        setHeight(HEIGHT);

        setPosition(radar.getPosition());

        draw();
    }

    public Plane getTarget() {
        return target;
    }

    public Radar getRadar() {
        return radar;
    }

    /**
     * Launch this missile so that it brings that target down.
     */
    public void fire() {
        if (flyTimer == null) {
            flyTimer = new Timer();
        }
        flyTimer.schedule(TrackTask.with(radar, target, this), 0, 100);
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
        notifyPositionChanged();
    }

    public void setY(int y) {
        setLayoutY(y);
        notifyPositionChanged();
    }

    private void notifyPositionChanged() {
        if (getLayoutY() >= MainController.getInstance().getMapHeight() - getHeight()
                || getLayoutX() >= MainController.getInstance().getMapWidth() - getWidth()) {
            System.out.println("Missile out of map. Removing...");
            flyTimer.cancel();
            MainController.getInstance().removeMissile(this);
        }
    }

    private void draw() {
        Canvas canvas = new Canvas(MissileView.WIDTH, MissileView.HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.web("#FF5722"));
        graphicsContext.fillOval(0, 0, MissileView.WIDTH, MissileView.HEIGHT);
        getChildren().clear();
        getChildren().add(canvas);
    }
}
