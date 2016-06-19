package tr.gediz.ars.controller;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.beans.*;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import tr.gediz.ars.Main;
import tr.gediz.ars.bus.Bus;
import tr.gediz.ars.bus.event.PlaneAddedEvent;
import tr.gediz.ars.bus.event.PlaneRemovedEvent;
import tr.gediz.ars.util.Log;
import tr.gediz.ars.view.MissileView;
import tr.gediz.ars.view.PlaneView;
import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Position;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.model.Threat;
import tr.gediz.ars.ui.cell.RadarListCell;
import tr.gediz.ars.ui.cell.ThreatListCell;
import tr.gediz.ars.view.RadarView;

import java.net.URL;
import java.util.*;

import static tr.gediz.ars.util.Log.log;

public class MainController implements Initializable {
    private static MainController instance;

    public Pane map;
    public ListView<String> logList;
    public ListView<Radar> radarsList;
    public ListView<Threat> threatsList;

    private ObservableList<Radar> radars = FXCollections.observableArrayList();
    private ObservableList<String> logMessages = FXCollections.observableArrayList();
    private ObservableList<Threat> threats = FXCollections.observableArrayList();

    public MainController() {
        Bus.get().register(this);

        radars.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Log.log(observable.toString());
            }
        });
    }

    public static MainController getInstance() {
        return instance;
    }

    public static void setInstance(MainController instance) {
        MainController.instance = instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logList.setItems(logMessages);

        /* Disable selection */
        radarsList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        radarsList.getSelectionModel().select(-1);
                    }
                });
            }
        });
        radarsList.setItems(radars);
        radarsList.setCellFactory(new Callback<ListView<Radar>, ListCell<Radar>>() {
            @Override
            public ListCell<Radar> call(ListView<Radar> radarListView) {
                return new RadarListCell();
            }
        });

        /* Disable selection */
        threatsList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        threatsList.getSelectionModel().select(-1);
                    }
                });
            }
        });
        threatsList.setItems(threats);
        threatsList.setCellFactory(new Callback<ListView<Threat>, ListCell<Threat>>() {
            @Override
            public ListCell<Threat> call(ListView<Threat> threatListView) {
                return new ThreatListCell();
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

    public void drawMissile(Radar radar, Plane target) {
        MissileView missileView = new MissileView(radar, target);
        map.getChildren().add(missileView);
        missileView.fire();
    }

    public void removeMissile(final MissileView missileView) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                map.getChildren().remove(missileView);
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

    public ArrayList<PlaneView> getPlaneViews() {
        ArrayList<PlaneView> result = new ArrayList<>();
        for (Node child : map.getChildren()) {
            if (child instanceof PlaneView) {
                result.add((PlaneView) child);
            }
        }
        return result;
    }

    public RadarView getRadarView(Radar radar) {
        RadarView found = null;
        for (RadarView rv : getRadarViews()) {
            if (rv.getRadar() == radar) {
                found = rv;
            }
        }
        return found;
    }

    public PlaneView getPlaneView(Plane plane) {
        PlaneView found = null;
        for (PlaneView pv : getPlaneViews()) {
            if (pv.getPlane() == plane) {
                found = pv;
            }
        }
        return found;
    }

    public void indicateTakedown(Position position) {
        final ImageView imageView = new ImageView("/image/cross.png");
        imageView.setX(position.getX() - imageView.getFitWidth() / 2);
        imageView.setY(position.getY() - imageView.getFitHeight() / 2);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                map.getChildren().add(imageView);
            }
        });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        map.getChildren().remove(imageView);
                    }
                });
            }
        }, 4000);
    }

    public int getMapWidth() {
        return (int) map.getWidth();
    }

    public int getMapHeight() {
        return (int) map.getHeight();
    }

    public void addThreat(final Threat threat) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (!threats.contains(threat)) {
                    threats.add(threat);
                }
            }
        });
    }

    public void removeThreat(final Threat threat) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                threats.remove(threat);
            }
        });
    }

    public void logMessage(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                logMessages.add(message);
                logList.scrollTo(logMessages.indexOf(message));
            }
        });
    }

    /**
     * Needs testing
     */
    public void removePlane(Plane plane) {
        try {
            for (final Node child : map.getChildren()) {
                if (child instanceof PlaneView && ((PlaneView) child).getPlane() == plane) {
                    ((PlaneView) child).stopFlying();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            map.getChildren().remove(child);
                        }
                    });
                    break;
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
            // Simply try again
            log("Failed when removing plane (ConcurrentModificationException). Trying again...");
            removePlane(plane);
        }
    }

    @Subscribe
    public void onPlaneAdded(PlaneAddedEvent event) {
        drawPlane(event.getPlane());
    }

    @Subscribe
    public void onPlaneRemoved(PlaneRemovedEvent event) {
        removePlane(event.getPlane());
        removeThreatForRemovedPlane(event.getPlane());
    }

    private void removeThreatForRemovedPlane(Plane plane) {
        if (threats.size() == 0) return;
        if (plane.getType() == Plane.Type.HOSTILE) {
            try {
                for (Threat t : threats) {
                    if (t.getTarget() == plane && threats.contains(t)) {
                        threats.remove(t);
                    }
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
                Log.log("Error while removing threat item for removed plane. Trying again...");
                removeThreatForRemovedPlane(plane);
            }
        }
    }
}
