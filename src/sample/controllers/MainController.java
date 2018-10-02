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
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menu.fxml"));
        //MainController.primaryStage = primaryStage;
        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public MainController() {}

    @FXML
    public void onSendButton(){
        primaryStage.close();
        try {
            new SendEMailController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void onReceiveButton(){
        primaryStage.close();
        try {
            new ReceiverController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

}
