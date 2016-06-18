package tr.gediz.ars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tr.gediz.ars.coordinator.PlaneCoordinator;
import tr.gediz.ars.model.Threat;

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

    public void planeButtonClicked() {
        PlaneCoordinator.interceptPlane(threat.getTarget(), threat.getRadar());
        interceptButtonsContainer.setVisible(false);
    }

    public void missileButtonClicked() {
        PlaneCoordinator.shootdownPlane(threat.getRadar(), threat.getTarget());
        interceptButtonsContainer.setVisible(false);
    }
}
