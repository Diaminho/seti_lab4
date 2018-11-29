package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.MyStage;
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
        MyStage myStage=new MyStage();
        String downFileName=myStage.showAndReturn(new InputFormController(myStage));
        System.out.println(downFileName);
        if(downFileName.compareTo("")!=0 && downFileName.charAt(0)!=' '){
            ftpManager.onDownloadButton(downFileName);
        }
    }

    @FXML
    public void onRenameButton() throws IOException, InterruptedException {
        MyStage myStage=new MyStage();
        String renameStr=myStage.showAndReturn(new InputFormController(myStage));
        System.out.println(renameStr);
        if(renameStr.compareTo("")!=0 && renameStr.charAt(0)!=' '){
            ftpManager.onRenameButton(renameStr);
        }
    }

    @FXML
    public void onUploadButton() throws IOException {
        ftpManager.onUploadButton();
    }

    @FXML
    public void onCreateDirButton() throws IOException {
        MyStage myStage=new MyStage();
        String newDirStr=myStage.showAndReturn(new InputFormController(myStage));
        System.out.println(newDirStr);
        if(newDirStr.compareTo("")!=0 && newDirStr.charAt(0)!=' '){
            ftpManager.onCreateDirButton(newDirStr);
        }

    }

    @FXML
    public void onChangeDirButton() throws IOException {
        ftpManager.onChangeDirButton();
    }

}
