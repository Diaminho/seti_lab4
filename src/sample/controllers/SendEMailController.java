package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.managers.SendEMailManager;

import java.io.IOException;

public class SendEMailController {

    private static Parent root;

    private static SendEMailManager sendEMailManager;

    private static Stage primaryStage;

    public SendEMailController(Stage primaryStage) throws IOException {
        //FXMLLoader loader=new FXMLLoader();
        //loader.setLocation(getClass().getResource("../fxml/sendEMail.fxml"));
        root = FXMLLoader.load(getClass().getResource("/sample/fxml/sendEMail.fxml"));
        //root=loader.load();
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Ввод данных для отправки сообщений");
        this.sendEMailManager = new SendEMailManager(root);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public SendEMailController() {}

    @FXML
    public void onSendEMailButton(){
        try {
            this.sendEMailManager.onSendEMailButton();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void onBackButton() {
        primaryStage.close();
        this.sendEMailManager.onBackButton();
    }
}
