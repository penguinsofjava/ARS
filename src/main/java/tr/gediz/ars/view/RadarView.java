package tr.gediz.ars.view;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;
import tr.gediz.ars.model.Position;
import tr.gediz.ars.model.Positionable;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.task.ScanTask;

import java.util.Timer;

public class RadarView extends Pane implements Positionable {
    private Radar radar;

    private Timer scannerTimer;
    private boolean scanning;

    private RotateTransition transition;

    public RadarView(Radar radar) {
        setRadar(radar);
    }

    public void setRadar(Radar radar) {
        this.radar = radar;

        setWidth(radar.getWidth() + 2); // +2 to avoid stroke cutoff
        setHeight(radar.getHeight() + 2);

        setPosition(radar.getPosition());

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
        setLayoutX(x - getWidth() / 2);
        setLayoutY(y - getHeight() / 2);
    }

    @Override
    public void setPosition(Position position) {
        setPosition(position.getX(), position.getY());
    }

    @Override
    public Position getPosition() {
        return new Position((int) getLayoutX(), (int) getLayoutY());
    }

    @Override
    public void moveInX(int by) {
        setLayoutX(getLayoutX() + by);
    }

    @Override
    public void moveInY(int by) {
        setLayoutY(getLayoutY() + by);
    }

    public void startScanning() {
        if (scannerTimer == null) {
            scannerTimer = new Timer();
        }
        toggleAnimation(true);
        radar.setScanAngleAlpha(45);
        scannerTimer.schedule(ScanTask.with(radar), 0, radar.getScanInterval() / (360 / radar.getScanAngle()));
        scanning = true;
    }

    public void stopScanning() {
        if (scannerTimer != null) {
            scannerTimer.cancel();
            scannerTimer = null;
        }
        toggleAnimation(false);
        scanning = false;
    }

    public boolean isScanning() {
        return scanning;
    }

    private void toggleAnimation(boolean animate) {
        if (transition == null) {
            transition = new RotateTransition(Duration.millis(radar.getScanInterval()), this);
            transition.setCycleCount(-1);
            transition.setByAngle(-360);
            transition.setInterpolator(new Interpolator() {
                @Override
                protected double curve(double v) {
                    radar.setScanAngleAlpha((int) (360 * v));
                    return v; // Linear interpolation
                }
            });
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
        graphicsContext.setFill(Color.web("#546E7A", 0.3));
        graphicsContext.setStroke(Color.web("#455A64", 0.3));
        graphicsContext.setLineWidth(1);
        graphicsContext.fillOval(1, 1, radar.getWidth(), radar.getHeight()); // Draw at (1, 1) to avoid stroke cutoff
        graphicsContext.strokeOval(1, 1, radar.getWidth(), radar.getHeight());
        graphicsContext.strokeArc(1, 1, radar.getWidth(), radar.getHeight(), radar.getScanAngle(), 360 - radar.getScanAngle(), ArcType.ROUND);
        getChildren().clear();
        getChildren().add(canvas);
    }
}
