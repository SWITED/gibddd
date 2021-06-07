package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarType {
    private int id;
    private String name;

    public CarType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ObservableList<CarType> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<CarType> carTypes = FXCollections.observableArrayList();
        while(resultSet.next()){
            carTypes.add(new CarType(resultSet.getInt("id"), resultSet.getString("name")));
        }

        return carTypes;
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
