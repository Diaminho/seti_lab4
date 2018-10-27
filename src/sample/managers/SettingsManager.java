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
import java.util.ArrayList;

public class SettingsManager {
    private static Parent root;
    TextField mailHostSMTPID;
    TextField portSMTPID;
    TextField logSMTPID;

    TextField mailHostPOP3ID;
    TextField portPOP3ID;
    TextField logPOP3ID;

    SettingsXml settingsXml;

    @FXML
    Stage primaryStage;

    public SettingsManager(Parent root) throws IOException, SAXException, ParserConfigurationException {
        this.root = root;
        init();

        settingsXml=new SettingsXml();
        ArrayList<String> settingsDef=settingsXml.readXMLFile("./src/sample/settings.xml");

        //for (int i=0;i<kek.size();i++){
            //System.out.println(kek.get(i));
        //}
        mailHostSMTPID.setText(settingsDef.get(0));
        portSMTPID.setText(settingsDef.get(1));
        logSMTPID.setText(settingsDef.get(2));
        mailHostPOP3ID.setText(settingsDef.get(3));
        portPOP3ID.setText(settingsDef.get(4));
        logPOP3ID.setText(settingsDef.get(5));

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
        //ArrayList<String> settingsChanged=new ArrayList<>();

        String[][] settingsChanged=new String[2][3];

        settingsChanged[0][0]=mailHostSMTPID.getText();
        settingsChanged[0][1]=(portSMTPID.getText());
        settingsChanged[0][2]=(logSMTPID.getText());
        settingsChanged[1][0]=(mailHostPOP3ID.getText());
        settingsChanged[1][1]=(portPOP3ID.getText());
        settingsChanged[1][2]=(logPOP3ID.getText());


        settingsXml.writeXMLFile("./src/sample/settings.xml",settingsChanged);


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
