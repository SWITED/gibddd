package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FreePlate {
    private int id;
    private String series;
    private int start_range;
    private int end_range;
    private int car_type_id;

    public FreePlate(int id, String series, int start_range, int end_range, int car_type_id) {
        this.id = id;
        this.series = series;
        this.start_range = start_range;
        this.end_range = end_range;
        this.car_type_id = car_type_id;
    }

    public static ObservableList<FreePlate> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<FreePlate> freePlates = FXCollections.observableArrayList();
        while(resultSet.next()){
            freePlates.add(new FreePlate(resultSet.getInt("id"), resultSet.getString("series"),
                    resultSet.getInt("start_range"), resultSet.getInt("end_range"), resultSet.getInt("car_type_id")));
        }

        return freePlates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getStart_range() {
        return start_range;
    }

    public void setStart_range(int start_range) {
        this.start_range = start_range;
    }

    public int getEnd_range() {
        return end_range;
    }

    public void setEnd_range(int end) {
        this.end_range = end_range;
    }

    public int getCar_type_id() {
        return car_type_id;
    }

    public void setCar_type_id(int car_type_id) {
        this.car_type_id = car_type_id;
    }
}
