package tr.gediz.ars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.view.RadarView;

/**
 * A JavaFx controller for the radar {@link ListView} items.
 */
public class RadarItemController {
    @FXML
    private Label name;
    @FXML
    private ImageView startStop;

    private RadarView radar;

    public void setRadar(Radar radar) {
        this.radar = MainController.getInstance().getRadarView(radar);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    /**
     * Toggles whether this radar is scanning ro not.
     */
    public void toggleRadar() {
        if (radar == null) return;

        if (radar.isScanning()) {
            radar.stopScanning();
            startStop.setImage(new Image("/image/icon/toggle_switch_off.png"));
        } else {
            radar.startScanning();
            startStop.setImage(new Image("/image/icon/toggle_switch_on.png"));
        }
    }
}
