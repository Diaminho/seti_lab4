package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Pop3;
import sample.controllers.MainController;

import java.io.IOException;

public class ReceiverManager {
    private static Parent root;
    TextField serverID;
    TextField loginID;
    PasswordField passID;
    TextArea receivedMessagesID;

    public ReceiverManager(Parent root){
        this.root=root;
        init();

    }

    private void init() {
        serverID= (TextField) root.lookup("#serverID");
        loginID=(TextField) root.lookup("#loginID");
        passID=(PasswordField) root.lookup("#passID");
        receivedMessagesID=(TextArea) root.lookup("#receivedMessagesID");
    }

    @FXML
    public void onReceiveMessagesButton() throws IOException {

        //String[] dataText=getDataFromTextArea();
        Pop3 mail = new Pop3(serverID.getText());
        if (mail != null) {
            if (mail.receive(loginID.getText(), passID.getText())) {
                System.out.println("Mail received.");
            } else {
                System.out.println("Connect to Pop3 server failed!");
            }
        }
        System.out.println("Done.");

    }

    @FXML
    public void onMenuButton() throws IOException{
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
