package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

        loadRadars();
    }

    private void loadRadars() {
        ArrayList<Radar> dummyRadars = new ArrayList<Radar>() {{
            add(new Radar(20, 40, 500, new int[]{300, 100}));
            add(new Radar(10, 100, 1000, new int[]{400, 200}));
            add(new Radar(null, null, 700, new int[]{800, 70}));
        }};

        controller.drawRadars(dummyRadars);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
