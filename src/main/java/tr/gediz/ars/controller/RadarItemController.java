package tr.gediz.ars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.view.RadarView;

import static tr.gediz.ars.Main.getController;

public class RadarItemController {
    @FXML
    private Label name;
    @FXML
    private ImageView startStop;

    private RadarView radar;

    public void setRadar(Radar radar) {
        this.radar = getController().getRadarView(radar);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

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
