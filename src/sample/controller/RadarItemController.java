package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.model.Position;

import java.net.URL;
import java.util.ResourceBundle;

public class RadarItemController implements Initializable {
    @FXML
    private Label position;
    @FXML
    private Button startStop;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startStop.setVisible(false);
    }

    public void setPosition(Position position) {
        this.position.setText("(" + position.getY() + ", " + position.getY() + ")");
    }

    public void setStartStopButtonText(String text) {
        startStop.setText(text);
    }

    public void setOnStartStopAction(EventHandler<ActionEvent> handler) {
        startStop.setOnAction(handler);
    }
}
