package sample.ui.cell;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.controller.RadarItemController;
import sample.model.Radar;
import sample.view.RadarView;

public class RadarListCell extends ListCell<Radar> {

    @Override
    public void updateItem(final Radar item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("resources/layout/item/radar.fxml"));

            HBox root;
            try {
                root = (HBox) loader.load();
            } catch (Exception e) {
                e.printStackTrace();
                setEmptyGraphic();
                return;
            }

            final RadarItemController controller = loader.getController();

            root.setPrefHeight(76);

            controller.setName(item.getName());
            controller.setOnStartStopAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    for (RadarView radarView : Main.getController().getRadarViews()) {
                        if (radarView.getRadar().equals(item)) {
                            if (radarView.isScanning()) {
                                radarView.stopScanning();
                                controller.setStartStopButtonText("START");
                            } else {
                                radarView.startScanning();
                                controller.setStartStopButtonText("STOP");
                            }
                        }
                    }
                }
            });

            setGraphic(root);
        } else {
            setEmptyGraphic();
        }
    }

    private void setEmptyGraphic() {
        Pane pane = new Pane();
        pane.setPrefHeight(0);
        setGraphic(pane);
    }
}