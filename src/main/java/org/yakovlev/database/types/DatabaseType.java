package org.yakovlev.database.types;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class DatabaseType {

    protected String name;

    public String getName(){return name;}

    public Label getFXLabel(){
        Label l = new Label(name);
        l.setAlignment(Pos.TOP_RIGHT);
        l.setId(name);
        return l;
    }

    public TextField getFXTextField(){
        TextField tf = new TextField();
        tf.setAlignment(Pos.TOP_RIGHT);
        tf.setId(name);
        return tf;
    }
}
