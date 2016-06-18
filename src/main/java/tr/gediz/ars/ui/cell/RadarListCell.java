package tr.gediz.ars.ui.cell;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import tr.gediz.ars.model.Radar;
import tr.gediz.ars.controller.RadarItemController;

public class RadarListCell extends ListCell<Radar> {

    @SuppressWarnings("Duplicates")
    @Override
    public void updateItem(final Radar item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/item/radar.fxml"));

            HBox root;
            try {
                root = (HBox) loader.load();
            } catch (Exception e) {
                e.printStackTrace();
                setEmptyGraphic();
                return;
            }

            final RadarItemController controller = loader.getController();
            controller.setRadar(item);

            controller.setName(item.getName());

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