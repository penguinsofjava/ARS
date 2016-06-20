package tr.gediz.ars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tr.gediz.ars.coordinator.PlaneCoordinator;
import tr.gediz.ars.model.Plane;
import tr.gediz.ars.model.Threat;
import tr.gediz.ars.view.MissileView;

/**
 * A JavaFX controller for the threats {@link ListView}.
 */
public class ThreatItemController {
    public ImageView planeButton;
    public ImageView missileButton;
    public HBox interceptButtonsContainer;

    @FXML
    private Label name;

    private Threat threat;

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setThreat(Threat threat) {
        this.threat = threat;
    }

    /**
     * Invoked when the plane button has been clicked.
     *
     * This method calls for a new {@link Plane} to intercept the {@link Plane} contained in this {@link Threat}.
     */
    public void planeButtonClicked() {
        PlaneCoordinator.interceptPlane(threat.getTarget(), threat.getRadar());
        interceptButtonsContainer.setVisible(false);
    }

    /**
     * Invoked when the missile button has been clicked.
     *
     * This method calls for a new {@link MissileView} to take down the {@link Plane} contained in this {@link Threat}.
     */
    public void missileButtonClicked() {
        PlaneCoordinator.shootdownPlane(threat.getRadar(), threat.getTarget());
        interceptButtonsContainer.setVisible(false);
    }
}
