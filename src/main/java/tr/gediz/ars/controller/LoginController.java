package tr.gediz.ars.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tr.gediz.ars.Main;
import tr.gediz.ars.coordinator.PlaneFactory;
import tr.gediz.ars.coordinator.RadarCoordinator;
import tr.gediz.ars.model.UserInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginController {
    public TextField id;
    public Label labelsample;
    public Button button1;
    public PasswordField pw1;

    private Scanner input;

    //private String USER_FILE;
    // private boolean idCheck = false;
    private int pwCheck = 0;
    private static final File USER_FILE = new File("files/users.txt");

    private static ArrayList<UserInfo> users = new ArrayList<>();

    private Stage stage;

    public void login() {
        // label.getLabelFor().setVisible(true);
        openFile();
        ScantoFile();
        closeFİle();

        for (int x = 0; x < users.size(); x++) {
            System.out.println("***");
            System.out.println(users.get(x).getId());
            System.out.println(users.get(x).getPw());
            System.out.println(pwCheck);
            if (id.getText().equals(users.get(x).getId())) {
                System.out.println("**");
                if (pw1.getText().equals(users.get(x).getPw())) {
                    pwCheck = 1;
                    showMainStage();
                }
            }
        }

        if (pwCheck == 0) {
            labelsample.setVisible(true);
            System.out.println("olmadıı");
        } else
            System.out.println("***");

        System.out.println(id.getText());
        System.out.println(pw1.getText());
    }

    public void showMainStage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/main.fxml"));
            Parent root = (Parent) loader.load();
            MainController.setInstance((MainController) loader.getController());
            Stage stage = new Stage();
            stage.setTitle("Radar HQ");
            stage.setScene(new Scene(root, 1471, 680));
            stage.show();

            RadarCoordinator.loadRadars();
            MainController.getInstance().drawRadars(RadarCoordinator.getRadars());

            PlaneFactory.start();

            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void openFile() {
        try {
            input = new Scanner(USER_FILE);

        } catch (SecurityException e) {
            System.out.println("You do not have write to acces");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ScantoFile() {
        try {
            Scanner input = new Scanner(USER_FILE);

            users.clear();
            while (input.hasNext()) {
                UserInfo user;
                String id = input.next();
                String pw = input.next();
                user = new UserInfo(id, pw);
                users.add(user);

            }

        } catch (SecurityException e) {
            e.printStackTrace();
            System.out.println("You do not have write access to this file!");
        } catch (NoSuchElementException nse) {
            System.out.println("No such element exception occurred");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeFİle() {
        input.close();
    }
}
