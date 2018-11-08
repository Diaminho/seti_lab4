package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.Pop3;
import sample.SettingsXml;
import sample.controllers.MainController;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class ReceiverManager {
    private static Parent root;
    TextField serverID;
    TextField loginID;
    TextField portID;
    TextField logsID;
    PasswordField passID;
    ListView messageListID;
    TextArea fullMessageID;
    Pop3 mail;
    int size;
    ArrayList settings;

    public ReceiverManager(Parent root) throws IOException, SAXException, ParserConfigurationException {
        this.root=root;
        init();
        settings=SettingsXml.readXMLFile(SettingsManager.fileName);
        serverID.setText(settings.get(3).toString());
        portID.setText(settings.get(4).toString());
        logsID.setText(settings.get(5).toString());

    }

    private void init() {
        serverID= (TextField) root.lookup("#serverID");
        logsID= (TextField) root.lookup("#logsID");
        loginID=(TextField) root.lookup("#loginID");
        portID=(TextField) root.lookup("#portID");
        fullMessageID=(TextArea) root.lookup("#fullMessageID");
        passID=(PasswordField) root.lookup("#passID");
        messageListID=(ListView) root.lookup("#messageListID");
    }

    @FXML
    public void onReceiveMessagesButton() throws IOException {
        messageListID.setVisible(true);
        //String[] dataText=getDataFromTextArea();
        mail = new Pop3(serverID.getText());
        mail.setPOP3_PORT(Integer.parseInt(portID.getText()));
        mail.setLogFile(logsID.getText());
        if (mail != null) {
            if (mail.auth(loginID.getText(), passID.getText())) {
                System.out.println("Mail received.");
            } else {
                System.out.println("Connect to Pop3 server failed!");
            }
        }
        System.out.println("Done.");
        ArrayList messages=mail.receiveMessages();
        size=messages.size();
        //messageListID=new ListView<ArrayList>(FXCollections.observableArrayList(messages));
        for (int i=0;i<messages.size();i++) {
            messageListID.getItems().add(messages.get(i));
        }
    }

    @FXML
    public void onMenuButton() throws IOException{
        if (mail!=null) {
            mail.quit();
        }
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteActiveMessage() throws IOException {
        int selectedMessage=messageListID.getSelectionModel().getSelectedIndex();
        //ArrayList messages=mail.receiveMessages();
        mail.deleteMessage(selectedMessage+1);
        messageListID.getItems().remove(selectedMessage);
        messageListID.refresh();
        fullMessageID.setVisible(false);
    }

    @FXML
    public void onDisconnectButton() throws IOException{
        if (mail!=null) {
            mail.quit();
        }
    }

    @FXML
    public void onOpenMessage() throws IOException{
        fullMessageID.setVisible(true);
        int selectedMessage=messageListID.getSelectionModel().getSelectedIndex();
        ArrayList fullMessage=mail.getFullMessage(selectedMessage+1);
        String tmpStr="";
        for (int i=0;i<fullMessage.size();i++) {
            tmpStr+=fullMessage.get(i).toString()+"\n";
        }
        fullMessageID.setText(tmpStr);

    }
}
