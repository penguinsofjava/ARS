package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RadarItemController implements Initializable {
    @FXML
    private Label name;
    @FXML
    private Button startStop;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startStop.setVisible(false);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setStartStopButtonText(String text) {
        startStop.setText(text);
    }

    public void setOnStartStopAction(EventHandler<ActionEvent> handler) {
        startStop.setOnAction(handler);
    }
}
