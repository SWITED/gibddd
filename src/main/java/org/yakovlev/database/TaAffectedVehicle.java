package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaAffectedVehicle {
    private int id;
    private int ta_id;
    private int plate_id;

    public TaAffectedVehicle(int id, int ta_id, int plate_id) {
        this.id = id;
        this.ta_id = ta_id;
        this.plate_id = plate_id;
    }

    public static ObservableList<TaAffectedVehicle> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<TaAffectedVehicle> taAffectedVehicles = FXCollections.observableArrayList();
        while(resultSet.next()){
            taAffectedVehicles.add(new TaAffectedVehicle(resultSet.getInt("id"), resultSet.getInt("ta_id"), resultSet.getInt("plate_id")));
        }

        return taAffectedVehicles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTa_id() {
        return ta_id;
    }

    public void setTa_id(int ta_id) {
        this.ta_id = ta_id;
    }

    public int getPlate_id() {
        return plate_id;
    }

    public void setPlate_id(int plate_id) {
        this.plate_id = plate_id;
    }
}
