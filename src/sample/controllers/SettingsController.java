package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.managers.SettingsManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class SettingsController {
    private static Parent root;

    private static SettingsManager settingsManager;

    private static Stage primaryStage;

    public SettingsController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/fxml/settings.fxml"));
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Настройки");
        this.settingsManager = new SettingsManager(root);
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
    }


    public SettingsController(){

    }

    @FXML
    public void onSaveButton() throws ParserConfigurationException, SAXException, IOException {
        settingsManager.onSaveButton();
        primaryStage.close();
        settingsManager.onCancelButton();
    }

    @FXML
    public void onCancelButton() {
        primaryStage.close();
        settingsManager.onCancelButton();
    }
}
