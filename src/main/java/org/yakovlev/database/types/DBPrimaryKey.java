package org.yakovlev.database.types;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DBPrimaryKey extends DatabaseType {

    public DBPrimaryKey(String name){
        this.name = name;
    }

    public Label getFXLabel(){
        Label l = new Label(name);
        l.setOpacity(0.0);
        l.setId(name);
        return l;
    }

    public TextField getFXTextField(){
        TextField tf = new TextField();
        tf.setOpacity(0.0);
        tf.setId(name);
        return tf;
    }
}
