package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.SMTP;
import sample.SettingsXml;
import sample.controllers.MainController;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class SendEMailManager {
    private static Parent root;
    TextField smtpServerID;
    TextField senderID;
    TextField portID;
    TextField logsID;
    PasswordField passwordID;
    TextField receiverID;
    TextArea dataTextID;

    ArrayList settings;

    @FXML
    Stage primaryStage;

    public SendEMailManager(Parent root) throws IOException, SAXException, ParserConfigurationException {
        this.root = root;
        init();

        settings=SettingsXml.readXMLFile(SettingsManager.fileName);
        smtpServerID.setText(settings.get(0).toString());
        portID.setText(settings.get(1).toString());
        logsID.setText(settings.get(2).toString());
    }



    private void init() {
        smtpServerID= (TextField) root.lookup("#smtpServerID");
        senderID=(TextField) root.lookup("#senderID");
        passwordID=(PasswordField) root.lookup("#passwordID");
        receiverID=(TextField) root.lookup("#receiverID");
        logsID=(TextField) root.lookup("#logsID");
        portID=(TextField) root.lookup("#portID");
        dataTextID=(TextArea) root.lookup("#dataTextID");
    }




    @FXML
    public void onSendEMailButton() throws IOException {
        String[] dataText=getDataFromTextArea();
        SMTP mail = new SMTP(smtpServerID.getText());
        mail.setSMTP_PORT(Integer.parseInt(portID.getText()));
        mail.setLogFile(logsID.getText());
        if (mail != null) {
            if (mail.send(dataText, senderID.getText(), receiverID.getText(), passwordID.getText())) {
                System.out.println("Mail sent.");
            } else {
                System.out.println("Connect to SMTP server failed!");
            }
        }
        System.out.println("Done.");

    }

    @FXML
    public void onBackButton() {
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    String[] getDataFromTextArea(){
        String[] dataText=new String[dataTextID.getParagraphs().size()];
        for (int i=0;i<dataText.length;i++){
            dataText[i]=dataTextID.getParagraphs().get(i).toString();
        }
        return dataText;
    }
}