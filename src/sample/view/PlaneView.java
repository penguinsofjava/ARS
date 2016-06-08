package sample.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.model.Plane;
import sample.model.Position;
import sample.model.Positionable;
import sample.task.FlyTask;

import java.util.Timer;

public class PlaneView extends Pane implements Positionable {
    private static final int WIDTH = 10; // px
    private static final int HEIGHT = 10; // px

    private Plane plane;

    private Timer flyTimer;

    public PlaneView(Plane plane) {
        setPlane(plane);
    }

    public void setPlane(Plane plane) {
        this.plane = plane;

        setWidth(WIDTH);
        setHeight(HEIGHT);

        draw();
    }

    public void startFlying() {
        if (flyTimer == null) {
            flyTimer = new Timer();
        }
        flyTimer.schedule(FlyTask.with(this), 0, plane.getSpeed());
    }

    public void stopFlying() {
        if (flyTimer != null) {
            flyTimer.cancel();
        }
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
    
    private void setX(int x) {
    	setLayoutX(x);
    	plane.getPosition().setX(x);
    }
    
    private void setY(int y) {
    	setLayoutY(y);
    	plane.getPosition().setY(y);
    }
    

    private void draw() {
        Canvas canvas = new Canvas(PlaneView.WIDTH, PlaneView.HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.web("#000000")); // TODO: Implement colors by state
        graphicsContext.fillOval(0, 0, PlaneView.WIDTH, PlaneView.HEIGHT);
        getChildren().clear();
        getChildren().add(canvas);
    }
}
