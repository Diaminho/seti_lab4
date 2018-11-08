package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public static Stage primaryStage;

    public MainController(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/menu.fxml"));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Главное меню");
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
    }

    public MainController() {}

    @FXML
    public void onSendButton(){
        primaryStage.close();
        try {
            new SendEMailController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onAboutButton(){
        primaryStage.close();
        try {
            new AboutController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onReceiveButton(){
        primaryStage.close();
        try {
            new ReceiverController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSettingsButton(){
        primaryStage.close();
        try {
            new SettingsController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
