package sample.view;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;
import sample.model.Position;
import sample.model.Radar;
import sample.scan.ScanTask;

import java.util.Timer;

public class RadarView extends Pane implements Position {
    private Radar radar;
    private Timer scannerTimer;
    private RotateTransition transition;

    public RadarView(Radar radar) {
        setRadar(radar);
    }

    public void setRadar(Radar radar) {
        this.radar = radar;

        setWidth(radar.getWidth() + 2); // +2 to avoid stroke cutoff
        setHeight(radar.getHeight() + 2);

        setPosition(radar.getPosition()[0], radar.getPosition()[1]);

        draw();

        /* Listen for changes*/
        radar.setListener(new Radar.ChangeListener() {
            @Override
            public void onChange() {
                draw();
            }
        });
    }

    public Radar getRadar() {
        return radar;
    }

    public void setPosition(int x, int y) {
        setLayoutX(x);
        setLayoutY(y);
    }

    @Override
    public int[] getPosition() {
        return new int[]{(int) getLayoutX(), (int) getLayoutY()};
    }

    @Override
    public void moveInX(int by) {
        setLayoutX(getLayoutX() + by);
    }

    @Override
    public void moveInY(int by) {
        setLayoutY(getLayoutY() + by);
    }

    @Override
    public int getPositionX() {
        return (int) getLayoutX();
    }

    @Override
    public int getPositionY() {
        return (int) getLayoutY();
    }

    public void startScanning() {
        if (scannerTimer == null) {
            scannerTimer = new Timer();
        }
        scannerTimer.schedule(ScanTask.with(radar), 0, radar.getScanInterval());
        toggleAnimation(true);
    }

    public void stopScanning() {
        if (scannerTimer != null) {
            scannerTimer.cancel();
        }
        toggleAnimation(false);
    }

    private void toggleAnimation(boolean animate) {
        if (transition == null) {
            transition = new RotateTransition(Duration.millis(radar.getScanInterval()), this);
            transition.setCycleCount(-1);
            transition.setByAngle(360);
            transition.setInterpolator(Interpolator.LINEAR);
        }
        if (animate) {
            transition.play();
        } else {
            transition.stop();
        }
    }

    private void draw() {
        Canvas canvas = new Canvas(radar.getWidth() + 2, radar.getHeight() + 2); // +2 to avoid stroke cutoff
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Radar.Color.ACTIVE.getFill());
        graphicsContext.setStroke(Radar.Color.ACTIVE.getStroke());
        graphicsContext.setLineWidth(1);
        graphicsContext.fillOval(1, 1, radar.getWidth(), radar.getHeight()); // Draw at (1, 1) to avoid stroke cutoff
        graphicsContext.strokeOval(1, 1, radar.getWidth(), radar.getHeight());
        graphicsContext.strokeArc(1, 1, radar.getWidth(), radar.getHeight(), radar.getRadius(), 360 - radar.getRadius(), ArcType.ROUND);
        getChildren().clear();
        getChildren().add(canvas);
    }
}
