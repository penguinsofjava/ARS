package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.MainController;
import sample.coordinator.PlaneCoordinator;
import sample.coordinator.PlaneFactory;
import sample.coordinator.RadarCoordinator;
import sample.model.Plane;

public class Main extends Application implements PlaneCoordinator.OnPlaneAddedListener, PlaneCoordinator.OnPlaneRemovedListener {
    private static MainController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/layout/main.fxml"));
        Parent root = (Parent) loader.load();
        controller = loader.getController();
        primaryStage.setTitle("Radar HQ");
        primaryStage.setScene(new Scene(root, 1358, 680));
        primaryStage.show();

        dummyContent();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    private void dummyContent() {
        RadarCoordinator radarCoordinators = new RadarCoordinator();
        radarCoordinators.setPath("radarRecord.txt");
        radarCoordinators.openFile();
        radarCoordinators.readRecords();

        controller.drawRadars(RadarCoordinator.getRadars());

        PlaneCoordinator.getAddListeners().add(this);
        PlaneCoordinator.getRemoveListeners().add(this);

        PlaneFactory.start();
    }

    public static MainController getController() {
        return controller;
    }

    @Override
    public void onPlaneAdded(Plane plane) {
        controller.drawPlane(plane);
    }

    @Override
    public void onPlaneRemoved(Plane plane) {
        controller.removePlane(plane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
