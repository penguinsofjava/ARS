package sample;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import sample.model.Plane;
import sample.model.Radar;
import sample.view.PlaneView;
import sample.view.RadarView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public static final int MAP_HEIGHT = 480; // px

    public Pane map;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void drawRadars(List<Radar> radars) {
        for (Radar radar : radars) {
            RadarView radarView = new RadarView(radar);
            map.getChildren().add(radarView);
            radarView.startScanning();
        }
    }

    public void drawPlanes(List<Plane> planes) {
        for (Plane plane : planes) {
            drawPlane(plane);
        }
    }

    public void drawPlane(Plane plane) {
        final PlaneView planeView = new PlaneView(plane);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                map.getChildren().add(planeView);
                planeView.startFlying();
            }
        });
    }

    /**
     * Needs testing
     */
    public void removePlane(Plane plane) {
        for (int i = 0; i < map.getChildren().size(); i++) {
            Node child = map.getChildren().get(i);
            if (child instanceof PlaneView && ((PlaneView) child).getPlane() == plane) {
                map.getChildren().remove(child);
            }
        }
    }
}
