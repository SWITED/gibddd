package org.yakovlev.controllers.filters;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.yakovlev.AppFX;

abstract public class FilterController {

    protected AppFX app;
    protected Stage dialogStage;

    public void setApp(AppFX app){
        this.app = app;
    }

    public void setDialogStage(Stage ds){
        dialogStage = ds;
    }

    public static String getLabelText(){return "";}
    public static String getFXML(){return "";}
    public static String getTitle(){return "";}

//    abstract public Node[] getFilters();

    abstract protected void filter() throws Exception;
}
