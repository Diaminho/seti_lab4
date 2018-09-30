package sample.managers;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;
import sample.SMTP;
import sample.controllers.MainController;

import java.io.IOException;

public class SendEMailManager {
    //Stage primaryStage;

    private static Parent root;


    @FXML
    Stage primaryStage;

    public SendEMailManager(Parent root) {
        SendEMailManager.root = root;
        init();
    }



    private void init() {
        /*
        textFieldPropValue= (TextField) root.lookup("#textFieldPropValue");
        textFieldPropName = (TextField) root.lookup("#textFieldPropName");
        textFieldMaterialName = (TextField) root.lookup("#textFieldMaterialName");
        textFieldMaterialPropCount=(TextField) root.lookup("#textFieldMaterialPropCount");
        */
    }




    @FXML
    public void onSendEMailButton() throws IOException {
        String data = "TEST MESSAGE FROM APP";
        String from = "setilab23@nn.ru";
        String to = "download4pda@yandex.ru";
        String password="";
        String mailHost = "mail.nn.ru";
        SMTP mail = new SMTP(mailHost);
        if (mail != null) {
            if (mail.send(data, from, to, password)) {
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
}