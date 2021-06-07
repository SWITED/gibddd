package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarBrand {

    private int id;
    private String brand_name;
    private String alarm_system;

    public CarBrand(int id, String brand_name, String alarm_system){
        this.id = id;
        this.brand_name = brand_name;
        this.alarm_system = alarm_system;
    }

    public static ObservableList<CarBrand> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<CarBrand> carBrands = FXCollections.observableArrayList();
        while(resultSet.next()){
            carBrands.add(new CarBrand(resultSet.getInt("id"), resultSet.getString("brand_name"), resultSet.getString("alarm_system")));
        }

        return carBrands;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getAlarm_system() {
        return alarm_system;
    }

    public void setAlarm_system(String alarm_system) {
        this.alarm_system = alarm_system;
    }
}
