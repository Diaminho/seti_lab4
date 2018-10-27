package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.SettingsXml;
import sample.controllers.MainController;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class SettingsManager {
    private static Parent root;
    TextField mailHostSMTPID;
    TextField portSMTPID;
    TextField logSMTPID;

    TextField mailHostPOP3ID;
    TextField portPOP3ID;
    TextField logPOP3ID;

    @FXML
    Stage primaryStage;

    public SettingsManager(Parent root) {
        this.root = root;
        init();
    }


    private void init() {
        mailHostSMTPID= (TextField) root.lookup("#mailHostSMTPID");
        portSMTPID=(TextField) root.lookup("#portSMTPID");
        logSMTPID=(TextField) root.lookup("#logSMTPID");

        mailHostPOP3ID= (TextField) root.lookup("#mailHostPOP3ID");
        portPOP3ID=(TextField) root.lookup("#portPOP3ID");
        logPOP3ID=(TextField) root.lookup("#logPOP3ID");

    }

    @FXML
    public void onSaveButton() throws IOException, SAXException, ParserConfigurationException {
        SettingsXml settingsXml=new SettingsXml();
        settingsXml.readXMLFile("./src/sample/settings.xml");
        //settingsXml.writeXMLFile("./src/sample/settings.xml");


    }

    @FXML
    public void onCancelButton(){
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
