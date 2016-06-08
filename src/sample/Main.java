package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.coordinator.PlaneCoordinator;
import sample.coordinator.PlaneFactory;
import sample.coordinator.RadarCoordinator;

public class Main extends Application {
    private static Controller controller;

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

    private void dummyContent() {
            RadarCoordinator radarCoordinators = new RadarCoordinator();
//            add(new Radar(0, 20, 40, 500, new Position(300, 100)));
            //add(new Radar(1, 45, 250, 1000, new Position(400, 200)));
        radarCoordinators.setPath("radarRecord.txt");
          radarCoordinators.openFile();
        radarCoordinators.readRecords();
//            add(new Radar(2, null, null, 700, new Position(800, 70)));

        controller.drawRadars(RadarCoordinator.getRadars());

        PlaneFactory.start();

        controller.drawPlanes(PlaneCoordinator.getPlanes());
    }

    public static Controller getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
