package sample.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class InputFormController {

    public static Stage primaryStage;
    private static Parent root;
    @FXML
    private TextField nameID;

    public TextField getNameID() {
        return nameID;
    }

    public void setNameID(TextField nameID) {
        this.nameID = nameID;
    }

    public InputFormController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/fxml/inputForm.fxml"));
        //this.root=root;
        init();
        //this.primaryStage = primaryStage;
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Форма ввода имени");
        this.primaryStage.setScene(new Scene(root));
        //this.primaryStage.show();
    }

    public InputFormController(){}

    private void init() {
        nameID= (TextField) root.lookup("#nameID");
    }

    @FXML
    public void onOkButton(){
        primaryStage.close();
    }

    @FXML
    public void onCancelButton(){
        primaryStage.close();
    }
}
