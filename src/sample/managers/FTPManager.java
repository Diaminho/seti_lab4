package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.FTP;
import sample.SettingsXml;
import sample.controllers.MainController;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class FTPManager {
    private static Parent root;
    TextField serverID;
    TextField loginID;
    TextField portID;
    TextField logsID;
    PasswordField passID;
    ListView fileListID;
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

    }


    private void updateFileList() throws IOException {
        List fileList= ftp.getFileList();
        size=fileList.size();
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

        for (int i=0;i<fileList.size();i++){
            fileListID.getItems().set(i,fileList.get(i));
        }
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
        //ArrayList messages=ftp.getFileList();
        ftp.deleteFile((String)fileListID.getItems().get(selectedFile));
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
    public void onDownloadButton() throws IOException{
        ftp.downloadFile((String)fileListID.getSelectionModel().getSelectedItem(),"text.txt");
    }

    @FXML
    public void onRenameButton() throws IOException {
        ftp.renameFileOrDir((String)fileListID.getSelectionModel().getSelectedItem(),"vector.txt");
        updateFileList();
    }
}
