package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TechnicalInspection {

    private int id;
    private int plate_id;
    private int is_passed;

    public TechnicalInspection(int id, int plate_id, int is_passed) {
        this.id = id;
        this.plate_id = plate_id;
        this.is_passed = is_passed;
    }

    public static ObservableList<TechnicalInspection> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<TechnicalInspection> technicalInspections = FXCollections.observableArrayList();
        while(resultSet.next()){
            technicalInspections.add(new TechnicalInspection(resultSet.getInt("id"), resultSet.getInt("plate_id"), resultSet.getInt("is_passed")));
        }

        return technicalInspections;
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

    public int getIs_passed() {
        return is_passed;
    }

    public void setIs_passed(int is_passed) {
        this.is_passed = is_passed;
    }
}
