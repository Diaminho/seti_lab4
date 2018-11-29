package sample;

import javafx.stage.Stage;
import sample.controllers.InputFormController;

public class MyStage extends Stage {
    public String showAndReturn(InputFormController controll) {
        super.showAndWait();
        return controll.getNameID().getText();
    }
}
