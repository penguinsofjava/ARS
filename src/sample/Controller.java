package sample;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import sample.model.Radar;
import sample.view.RadarView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
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
}
