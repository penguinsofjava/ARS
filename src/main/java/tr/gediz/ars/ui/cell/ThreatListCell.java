package tr.gediz.ars.ui.cell;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import tr.gediz.ars.controller.ThreatItemController;
import tr.gediz.ars.model.Threat;

/**
 * A {@link ListCell} used for displaying {@link Threat} items in a {@link ListView} to the right of the map.
 */
public class ThreatListCell extends ListCell<Threat> {

    @SuppressWarnings("Duplicates")
    @Override
    public void updateItem(final Threat item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/item/threat.fxml"));

            HBox root;
            try {
                root = (HBox) loader.load();
            } catch (Exception e) {
                e.printStackTrace();
                setEmptyGraphic();
                return;
            }

            final ThreatItemController controller = loader.getController();
            controller.setThreat(item);

            controller.setName(item.getTarget().getModel());

            setGraphic(root);
        } else {
            setEmptyGraphic();
        }
    }

    private void setEmptyGraphic() {
        try {
            Pane pane = new Pane();
            pane.setPrefHeight(0);
            setGraphic(pane);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}