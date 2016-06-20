package tr.gediz.ars.controller;

import com.google.common.eventbus.EventBus;
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

/**
 * A JavaFX controller for the main application window.
 *
 * A singleton becomes ready as soon as this class is instantiated.
 */
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
        instance = this;
        Bus.get().register(this);

        radars.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Log.log(observable.toString());
            }
        });
    }

    /**
     * Returns the singleton instance of this controller.
     */
    public static MainController getInstance() {
        return instance;
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

    /**
     * Draws a {@link List} of {@link Radar}s on the map and makes each of the radars start scanning.
     *
     * @param radars Radars to be added to the map.
     */
    public void drawRadars(List<Radar> radars) {
        for (Radar radar : radars) {
            RadarView radarView = new RadarView(radar);
            map.getChildren().add(radarView);
            radarView.startScanning();
        }
        this.radars.addAll(radars);
    }

    /**
     * Draws a {@link List} of {@link Plane}s on the map and makes each of the planes start flying.
     *
     * @param planes Planes to be added to the map.
     */
    public void drawPlanes(List<Plane> planes) {
        for (Plane plane : planes) {
            drawPlane(plane);
        }
    }

    /**
     * Draws a {@link Plane} on the map.
     *
     * @param plane Plane to be drawn.
     */
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

    /**
     * Draws a missile on the map and "fires" it towards the target {@link Plane}.
     *
     * @param radar Radar that detected the {@link Plane}.
     * @param target Target {@link Plane} to be shot down.
     */
    public void drawMissile(Radar radar, Plane target) {
        MissileView missileView = new MissileView(radar, target);
        map.getChildren().add(missileView);
        missileView.fire();
    }

    /**
     * Removes a {@link MissileView} from the map.
     *
     * @param missileView {@link MissileView} to be removed.
     */
    public void removeMissile(final MissileView missileView) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                map.getChildren().remove(missileView);
            }
        });
    }

    /**
     * Returns {@link List} of {@link RadarView}s that are currently displayed on the map.
     *
     * @return {@link List} of {@link RadarView}s.
     */
    public ArrayList<RadarView> getRadarViews() {
        ArrayList<RadarView> result = new ArrayList<>();
        for (Node child : map.getChildren()) {
            if (child instanceof RadarView) {
                result.add((RadarView) child);
            }
        }
        return result;
    }

    /**
     * Returns {@link List} of {@link PlaneView}s that are currently displayed on the map.
     *
     * @return {@link List} of {@link PlaneView}s.
     */
    public ArrayList<PlaneView> getPlaneViews() {
        ArrayList<PlaneView> result = new ArrayList<>();
        for (Node child : map.getChildren()) {
            if (child instanceof PlaneView) {
                result.add((PlaneView) child);
            }
        }
        return result;
    }

    /**
     * Returns a {@link RadarView} that displays the given {@link Radar} on the map.
     *
     * @param radar {@link Radar} that is displayed by the {@link RadarView} we are looking for.
     * @return The {@link RadarView}.
     */
    public RadarView getRadarView(Radar radar) {
        RadarView found = null;
        for (RadarView rv : getRadarViews()) {
            if (rv.getRadar() == radar) {
                found = rv;
            }
        }
        return found;
    }

    /**
     * Returns a {@link PlaneView} that displays the given {@link Plane} on the map.
     *
     * @param plane {@link Plane} that is displayed by the {@link PlaneView} we are looking for.
     * @return The {@link PlaneView}.
     */
    public PlaneView getPlaneView(Plane plane) {
        PlaneView found = null;
        for (PlaneView pv : getPlaneViews()) {
            if (pv.getPlane() == plane) {
                found = pv;
            }
        }
        return found;
    }

    /**
     * Draws a red x sign on the map to indicate that a takedown has taken place on the given position.
     *
     * The sign goes away after 4000 milliseconds.
     *
     * @param position {@link Position} on which the sign will be drawn.
     */
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

    /**
     * Returns the width of the map.
     * @return The width of the map in pixels.
     */
    public int getMapWidth() {
        return (int) map.getWidth();
    }

    /**
     * Returns the height of the map.
     * @return The height of the map in pixels.
     */
    public int getMapHeight() {
        return (int) map.getHeight();
    }

    /**
     * Adds a given {@link Threat} to the {@link #threats} {@link ArrayList} in order for it to be displayed in the
     * {@link ListView} to the right of the map.
     *
     * @param threat {@link Threat} to be added.
     */
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

    /**
     * Removes a given {@link Threat} from the {@link #threats} {@link ArrayList}.
     *
     * @param threat {@link Threat} to be removed.
     */
    public void removeThreat(final Threat threat) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                threats.remove(threat);
            }
        });
    }

    /**
     * Adds a new line of log message to the {@link #logList} {@link ListView}.
     *
     * @param message Log message to be added.
     */
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
     * Removes a {@link Plane} from the map.
     *
     * This method searches through the children of {@link #map} and tries to find the {@link PlaneView} that displays
     * this {@link Plane}. It then tries to remove the found {@link PlaneView}. In the case that a
     * {@link ConcurrentModificationException} is thrown, it calls itself recursively, thus trying again.
     *
     * As for the reason for the aforementioned exception to be thrown, we believe that it is caused by unknown-at-runtime
     * modifications to the {@link #map}'s children.
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

    /**
     * An {@link EventBus} subscriber method that listens for {@link PlaneAddedEvent}s.
     *
     * @param event {@link PlaneAddedEvent} that is published.
     */
    @Subscribe
    public void onPlaneAdded(PlaneAddedEvent event) {
        drawPlane(event.getPlane());
    }

    /**
     * An {@link EventBus} subscriber method that listens for {@link PlaneRemovedEvent}s.
     *
     * @param event {@link PlaneRemovedEvent} that is published.
     */
    @Subscribe
    public void onPlaneRemoved(PlaneRemovedEvent event) {
        removePlane(event.getPlane());
        removeThreatForRemovedPlane(event.getPlane());
    }

    /**
     * Removes a {@link Threat} for a given {@link Plane}.
     *
     * This method searches through {@link #threats} and tries to find the {@link Threat} that contains
     * the given {@link Plane}. It then tries to remove the found {@link Threat}. In the case that a
     * {@link ConcurrentModificationException} is thrown, it calls itself recursively, thus trying again.
     *
     * As for the reason for the aforementioned exception to be thrown, we believe that it is caused by unknown-at-runtime
     * modifications to the {@link #map}'s children.
     */
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
