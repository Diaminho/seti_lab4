package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.managers.FTPManager;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class FTPController {

    private static Parent root;

    private static FTPManager ftpManager;

    private static Stage primaryStage;


    public FTPController(Stage primaryStage) throws IOException, ParserConfigurationException, SAXException {
        root = FXMLLoader.load(getClass().getResource("/sample/fxml/ftpClient.fxml"));
        this.primaryStage=primaryStage;
        primaryStage.setTitle("FTP клиент");
        this.ftpManager = new FTPManager(root);
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
    }

    public FTPController() {}

    @FXML
    public void onOkButton(){
        try {
            this.ftpManager.onOkButton();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void onMenuButton() {
        primaryStage.close();
        try {
            this.ftpManager.onMenuButton();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void onDeleteButton() throws IOException {
        ftpManager.onDeleteButton();
    }

    @FXML
    public void onDisconnectButton() throws IOException {
        ftpManager.onDisconnectButton();
    }

    @FXML
    public void onDownloadButton() throws IOException {
        ftpManager.onDownloadButton();
    }

    @FXML
    public void onRenameButton() throws IOException {
        ftpManager.onRenameButton();
    }

}
