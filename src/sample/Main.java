package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.coordinator.PlaneCoordinator;
import sample.model.Plane;
import sample.model.Position;
import sample.model.Radar;

import java.util.ArrayList;

public class Main extends Application {
    private Controller controller;

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
        ArrayList<Radar> dummyRadars = new ArrayList<Radar>() {{
//            add(new Radar(0, 20, 40, 500, new Position(300, 100)));
            add(new Radar(1, 45, 250, 10000, new Position(400, 200)));
//            add(new Radar(2, null, null, 700, new Position(800, 70)));
        }};

        controller.drawRadars(dummyRadars);

        ArrayList<Plane> dummyPlanes = new ArrayList<Plane>() {{
            add(new Plane("F-16", 1000, new Position[]{new Position(200, 100)}));
            add(new Plane("F-16", 1000, new Position[]{new Position(350, 350)}));
            add(new Plane("F-16", 1000, new Position[]{new Position(100, 350)}));
            add(new Plane("F-16", 1000, new Position[]{new Position(500, 150)}));
        }};

        PlaneCoordinator.setPlanes(dummyPlanes);
        controller.drawPlanes(PlaneCoordinator.getPlanes());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
