package org.yakovlev.controllers;

import javafx.fxml.FXML;
import org.yakovlev.AppFX;

public class SelectModeController {

    private AppFX app;

    public void setApp(AppFX app){
        this.app = app;
    }

    @FXML
    private void selectedFiltrationMode() throws Exception {
        app.showFiltrationModeWindow();
    }

    @FXML
    private void selectedTablesMode() throws Exception {
        app.showTablesModeWindow();
    }
}
