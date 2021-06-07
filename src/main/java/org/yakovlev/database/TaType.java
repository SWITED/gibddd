package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaType {
    private int id;
    private String name;

    public TaType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ObservableList<TaType> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<TaType> taTypes = FXCollections.observableArrayList();
        while(resultSet.next()){
            taTypes.add(new TaType(resultSet.getInt("id"), resultSet.getString("name")));
        }

        return taTypes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
