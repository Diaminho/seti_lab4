package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutController {
    String text1="Программа является клиентом для протокола FTP.\n" +
            "\nДля функционирования программы необходимо, чтобы файл с настройками settings.xml располагался в папке ./ (текущей папке)\n" +
            "\nРазработали студенты группы ПМИМ-71: Пичугин Д.П. и Хачатрян Д.Р. 2018 год ©";
    TextFlow textFlowID;
    public static Stage primaryStage;

    public AboutController(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/about.fxml"));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("О программе");
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();

        textFlowID=(TextFlow) root.lookup("#textFlowID");
        Text textID=new Text();
        textID.setText(text1);
        textFlowID.getChildren().add(textID);

    }


    public AboutController() {}

    @FXML
    public void onMenuButton(){
        primaryStage.close();
        try {
            new MainController(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
