package sample.managers;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.SMTP;
import sample.controllers.MainController;

import java.io.IOException;

public class SendEMailManager {
    //Stage primaryStage;

    private static Parent root;
    TextField smtpServerID;
    TextField senderID;
    PasswordField passwordID;
    TextField receiverID;
    TextArea dataTextID;


    @FXML
    Stage primaryStage;

    public SendEMailManager(Parent root) {
        this.root = root;
        init();
    }



    private void init() {
        smtpServerID= (TextField) root.lookup("#smtpServerID");
        senderID=(TextField) root.lookup("#senderID");
        passwordID=(PasswordField) root.lookup("#passwordID");
        receiverID=(TextField) root.lookup("#receiverID");
        dataTextID=(TextArea) root.lookup("#dataTextID");
    }




    @FXML
    public void onSendEMailButton() throws IOException {
        /*
        String data = "TEST MESSAGE FROM APP";
        String from = "setilab23@nn.ru";
        String to = "download4pda@yandex.ru";
        String password="";
        String mailHost = "mail.nn.ru";
        */
        String[] dataText=getDataFromTextArea();
        SMTP mail = new SMTP(smtpServerID.getText());
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