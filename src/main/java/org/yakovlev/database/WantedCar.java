package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class WantedCar {

    private int id;
    private int plate_id;
    private Date theft_date;
    private int is_found;

    public WantedCar(int id, int plate_id, Date theft_date, int is_found) {
        this.id = id;
        this.plate_id = plate_id;
        this.theft_date = theft_date;
        this.is_found = is_found;
    }

    public static ObservableList<WantedCar> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<WantedCar> wantedCars = FXCollections.observableArrayList();
        while(resultSet.next()){
            wantedCars.add(new WantedCar(resultSet.getInt("id"), resultSet.getInt("plate_id"), resultSet.getDate("theft_date"), resultSet.getInt("is_found")));
        }

        return wantedCars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlate_id() {
        return plate_id;
    }

    public void setPlate_id(int plate_id) {
        this.plate_id = plate_id;
    }

    public Date getTheft_date() {
        return theft_date;
    }

    public void setTheft_date(Date theft_date) {
        this.theft_date = theft_date;
    }

    public int getIs_found() {
        return is_found;
    }

    public void setIs_found(int is_found) {
        this.is_found = is_found;
    }
}
