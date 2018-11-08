package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.managers.ReceiverManager;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class ReceiverController {

    private static Parent root;

    private static ReceiverManager receiverManager;

    private static Stage primaryStage;


    public ReceiverController(Stage primaryStage) throws IOException, ParserConfigurationException, SAXException {
        root = FXMLLoader.load(getClass().getResource("/sample/fxml/receiveEMail.fxml"));
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Ввод данных для получения сообщений");
        this.receiverManager = new ReceiverManager(root);
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
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

    @FXML
    public void onDeleteActiveMessage() throws IOException {
        receiverManager.onDeleteActiveMessage();
    }

    @FXML
    public void onDisconnectButton() throws IOException {
        receiverManager.onDisconnectButton();
    }

    @FXML
    public void onOpenMessage() throws IOException {
        receiverManager.onOpenMessage();
    }

}
