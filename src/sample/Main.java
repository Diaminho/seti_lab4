package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.MainController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/menu.fxml"));
        MainController.primaryStage = primaryStage;
        primaryStage.setTitle("SMTP и POP3 клиент");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        //XML WORKING
        SettingsXml settingsXml=new SettingsXml();
        String[][] kek=settingsXml.readXMLFile("./src/sample/settings.xml");
        for (int i=0;i<kek.length;i++){
            for (int j=0;j<kek[i].length;j++){
                System.out.println(kek[i][j]);
            }
        }


        String[][] str=new String[2][3];
        for (int i=0;i<str.length;i++){
            for (int j=0;j<str[i].length;j++){
                str[i][j]=""+i+j;
            }
        }
        settingsXml.writeXMLFile("./src/sample/settings.xml",str);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
