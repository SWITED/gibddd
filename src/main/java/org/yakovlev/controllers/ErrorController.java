package org.yakovlev.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorController {

    private Stage dialogStage;

    @FXML
    private Label error_text;

    public void setDialogStage(Stage ds){
        dialogStage = ds;
    }

    @FXML
    private void closeWindow(){
        dialogStage.close();
    }
}
