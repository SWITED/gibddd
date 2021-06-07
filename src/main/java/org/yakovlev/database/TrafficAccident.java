package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TrafficAccident {

    private int id;
    private Date ta_date;
    private int ta_type_id;
    private String place;
    private String description;
    private int victims_number;
    private float damage_amount;
    private String reason;
    private String road_conditions;

    public TrafficAccident(int id, Date ta_date, int ta_type_id, String place, String description, int victims_number, float damage_amount, String reason, String road_conditions) {
        this.id = id;
        this.ta_date = ta_date;
        this.ta_type_id = ta_type_id;
        this.place = place;
        this.description = description;
        this.victims_number = victims_number;
        this.damage_amount = damage_amount;
        this.reason = reason;
        this.road_conditions = road_conditions;
    }

    public static ObservableList<TrafficAccident> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<TrafficAccident> trafficAccidents = FXCollections.observableArrayList();
        while(resultSet.next()){
            trafficAccidents.add(new TrafficAccident(resultSet.getInt("id"), resultSet.getDate("ta_date"), resultSet.getInt("ta_type_id"),
                    resultSet.getString("place"), resultSet.getString("description"), resultSet.getInt("victims_number"),
                    resultSet.getFloat("damage_amount"), resultSet.getString("reason"), resultSet.getString("road_conditions")));
        }

        return trafficAccidents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTa_date() {
        return ta_date;
    }

    public void setTa_date(Date ta_date) {
        this.ta_date = ta_date;
    }

    public int getTa_type_id() {
        return ta_type_id;
    }

    public void setTa_type_id(int ta_type_id) {
        this.ta_type_id = ta_type_id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVictims_number() {
        return victims_number;
    }

    public void setVictims_number(int victims_number) {
        this.victims_number = victims_number;
    }

    public float getDamage_amount() {
        return damage_amount;
    }

    public void setDamage_amount(float damage_amount) {
        this.damage_amount = damage_amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRoad_conditions() {
        return road_conditions;
    }

    public void setRoad_conditions(String road_conditions) {
        this.road_conditions = road_conditions;
    }
}
