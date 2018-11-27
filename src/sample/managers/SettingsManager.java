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
    TextField serverID;
    TextField portID;
    TextField logID;

    static String fileName="./settings.xml";
    SettingsXml settingsXml;


    public SettingsManager(Parent root) throws IOException, SAXException, ParserConfigurationException {
        this.root = root;
        init();

        settingsXml=new SettingsXml();
        ArrayList<String> settingsDef=settingsXml.readXMLFile(fileName);

        serverID.setText(settingsDef.get(0));
        portID.setText(settingsDef.get(1));
        logID.setText(settingsDef.get(2));
    }

    private void init() {
        serverID= (TextField) root.lookup("#serverID");
        portID=(TextField) root.lookup("#portID");
        logID=(TextField) root.lookup("#logID");
    }

    @FXML
    public void onSaveButton() throws IOException, SAXException, ParserConfigurationException {
        //ArrayList<String> settingsChanged=new ArrayList<>();

        String[] settingsChanged=new String[3];

        settingsChanged[0]=serverID.getText();
        settingsChanged[1]=(portID.getText());
        settingsChanged[2]=(logID.getText());

        settingsXml.writeXMLFile(fileName,settingsChanged);
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
