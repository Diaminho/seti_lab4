package sample.managers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.FTP;
import sample.SettingsXml;
import sample.controllers.InputFormController;
import sample.controllers.MainController;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FTPManager {
    private static Parent root;
    TextField serverID;
    TextField loginID;
    TextField portID;
    TextField logsID;
    PasswordField passID;
    ListView fileListID;
    Label pathID;
    FTP ftp;
    int size;
    List settings;

    private final Image IMAGE_FOLDER  = new Image("/sample/images/folder.png", 16, 16, false, false);
    private final Image IMAGE_FILE  = new Image("/sample/images/file.png",16, 16, false, false);

    private Image[] listOfImages = {IMAGE_FOLDER, IMAGE_FILE};

    public FTPManager(Parent root) throws IOException, SAXException, ParserConfigurationException {
        this.root=root;
        init();
        settings=SettingsXml.readXMLFile(SettingsManager.fileName);
        serverID.setText(settings.get(0).toString());
        portID.setText(settings.get(1).toString());
        logsID.setText(settings.get(2).toString());

    }

    private void init() {
        serverID= (TextField) root.lookup("#serverID");
        logsID= (TextField) root.lookup("#logsID");
        loginID=(TextField) root.lookup("#loginID");
        portID=(TextField) root.lookup("#portID");
        passID=(PasswordField) root.lookup("#passID");
        fileListID=(ListView) root.lookup("#fileListID");
        pathID=(Label) root.lookup("#pathID");
    }

    @FXML
    public void onOkButton() throws IOException {
        fileListID.setVisible(true);
        ftp = new FTP(serverID.getText());
        ftp.setFTP_PORT(Integer.parseInt(portID.getText()));
        ftp.setLogFile(logsID.getText());
        if (ftp != null) {
            if (ftp.auth(loginID.getText(), passID.getText())) {
                System.out.println("Auth OK!");
            } else {
                System.out.println("Connect to FTP server failed!");
            }
        }
        System.out.println("Done.");
        updateFileList();
        pathID.setText(ftp.getCurrentDir());
    }


    private void updateFileList() throws IOException {
        List fileList= ftp.getFileList();
        size=fileList.size();
        fileListID.getSelectionModel().clearSelection();
        fileListID.getItems().clear();
        //fileListID=new ListView<ArrayList>(FXCollections.observableArrayList(fileList));
        for (int i=0;i<fileList.size();i++) {
            fileListID.getItems().add(fileList.get(i));
        }

        fileListID.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if((int)ftp.getFlags().get(fileList.indexOf(name))==0) {
                        imageView.setImage(listOfImages[1]);
                    }
                    else {
                        imageView.setImage(listOfImages[0]);
                    }
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
    }

    @FXML
    public void onMenuButton() throws IOException{
        if (ftp !=null) {
            ftp.quit();
        }
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteButton() throws IOException {
        int selectedFile=fileListID.getSelectionModel().getSelectedIndex();
        //IF FILE SELECTED
        if ((Integer)ftp.getFlags().get(selectedFile)==0) {
            ftp.deleteFile((String) fileListID.getItems().get(selectedFile));
        }
        //IF FOLDER SELECTED
        else{
            ftp.deleteFolder((String) fileListID.getItems().get(selectedFile));
        }
        fileListID.getItems().remove(selectedFile);
        fileListID.refresh();
    }

    @FXML
    public void onDisconnectButton() throws IOException{
        if (ftp !=null) {
            ftp.quit();
        }
    }

    @FXML
    public void onDownloadButton(String saveName) throws IOException{
        ftp.downloadFile((String)fileListID.getSelectionModel().getSelectedItem(),saveName);
    }

    @FXML
    public void onRenameButton(String newName) throws IOException {
        String oldName=(String)fileListID.getSelectionModel().getSelectedItem();
        fileListID.getSelectionModel().clearSelection();
        ftp.renameFileOrDir(oldName,newName);
        updateFileList();
    }

    @FXML
    public void onUploadButton() throws IOException {
        String choosenFile=getFileFromFileChooser();
        ftp.uploadFile(choosenFile);
        updateFileList();
    }

    @FXML
    public void onChangeDirButton() throws IOException {
        ftp.changeDir((String)fileListID.getSelectionModel().getSelectedItem());
        updateFileList();
        pathID.setText(ftp.getCurrentDir());
    }

    @FXML
    public void onCreateDirButton(String dir) throws IOException {
        ftp.createDir(dir);
        updateFileList();
    }


    public String getFileFromFileChooser(){
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            String fileName=file.getAbsolutePath();
            System.out.println("Имя файла: "+fileName);
            //file.;
            return fileName;
        }
        else return "None";
    }
}
