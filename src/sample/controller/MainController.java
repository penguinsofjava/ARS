package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import sample.model.Plane;
import sample.model.Radar;
import sample.ui.cell.RadarListCell;
import sample.view.PlaneView;
import sample.view.RadarView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Pane map;
    public ListView<String> logList;
    public ListView<Radar> radarsList;

    private ObservableList<Radar> radars = FXCollections.observableArrayList();
    private ObservableList<String> logMessages = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logList.setItems(logMessages);
        radarsList.setItems(radars);
        radarsList.setCellFactory(new Callback<ListView<Radar>, ListCell<Radar>>() {
            @Override
            public ListCell<Radar> call(ListView<Radar> radarListView) {
                return new RadarListCell();
            }
        });
    }

    public void drawRadars(List<Radar> radars) {
        for (Radar radar : radars) {
            RadarView radarView = new RadarView(radar);
            map.getChildren().add(radarView);
            radarView.startScanning();
        }
        this.radars.addAll(radars);
    }

    public void drawPlanes(List<Plane> planes) {
        for (Plane plane : planes) {
            drawPlane(plane);
        }
    }

    public void drawPlane(final Plane plane) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                PlaneView planeView = new PlaneView(plane);
                map.getChildren().add(planeView);
                planeView.startFlying();
            }
        });
    }

    public ArrayList<RadarView> getRadarViews() {
        ArrayList<RadarView> result = new ArrayList<>();
        for (Node child : map.getChildren()) {
            if (child instanceof RadarView) {
                result.add((RadarView) child);
            }
        }
        return result;
    }

    public int getMapWidth() {
        return (int) map.getWidth();
    }

    public int getMapHeight() {
        return (int) map.getHeight();
    }

    public void logMessage(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                logMessages.add(message);
            }
        });
    }

    /**
     * Needs testing
     */
    public void removePlane(Plane plane) {
        for (final Node child : map.getChildren()) {
            if (child instanceof PlaneView && ((PlaneView) child).getPlane() == plane) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        map.getChildren().remove(child);
                    }
                });
            }
        }
    }
}
