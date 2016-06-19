package tr.gediz.ars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tr.gediz.ars.controller.LoginController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/login.fxml"));
        Parent root = (Parent) loader.load();
        LoginController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setTitle("Air Radar Simulator");
        primaryStage.setScene(new Scene(root, 425, 250));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
