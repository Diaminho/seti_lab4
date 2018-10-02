package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.managers.ReceiverManager;


import java.io.IOException;


public class ReceiverController {

    private static Parent root;

    private static ReceiverManager receiverManager;

    private static Stage primaryStage;


    public ReceiverController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../fxml/receiveEMail.fxml"));
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Ввод данных для получения сообщений");
        this.receiverManager = new ReceiverManager(root);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public ReceiverController() {}

    @FXML
    public void onReceiveMessagesButton(){
        try {
            this.receiverManager.onReceiveMessagesButton();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void onMenuButton() {
        primaryStage.close();
        try {
            this.receiverManager.onMenuButton();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }


}
