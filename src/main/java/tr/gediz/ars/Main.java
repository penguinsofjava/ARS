package tr.gediz.ars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tr.gediz.ars.controller.MainController;
import tr.gediz.ars.coordinator.PlaneFactory;
import tr.gediz.ars.coordinator.RadarCoordinator;

public class Main extends Application {
    private static MainController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/main.fxml"));
        Parent root = (Parent) loader.load();
        controller = loader.getController();
        primaryStage.setTitle("Radar HQ");
        primaryStage.setScene(new Scene(root, 1471, 680));
        primaryStage.show();

        RadarCoordinator.loadRadars();
        controller.drawRadars(RadarCoordinator.getRadars());

        PlaneFactory.start();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static MainController getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
