package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Plate extends TableClass{

    private int id;
    private String plate;
    private String owner_full_name;
    private String owner_address;
    private int brand_id;
    private Date release_date;
    private float engine_capacity;
    private String engine_number;
    private String chassis_number ;
    private String body_number;
    private String color;
    private int for_organization;
    private int organization_id;
    private Date addition_date;

    public Plate(int id, String plate, String owner_full_name, String owner_address, int brand_id, Date release_date, float engine_capacity, String engine_number, String chassis_number, String body_number, String color, int for_organization, int organization_id, Date addition_date) {
        this.id = id;
        this.plate = plate;
        this.owner_full_name = owner_full_name;
        this.owner_address = owner_address;
        this.brand_id = brand_id;
        this.release_date = release_date;
        this.engine_capacity = engine_capacity;
        this.engine_number = engine_number;
        this.chassis_number = chassis_number;
        this.body_number = body_number;
        this.color = color;
        this.for_organization = for_organization;
        this.organization_id = organization_id;
        this.addition_date = addition_date;
    }

    public static ObservableList<Plate> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<Plate> plates = FXCollections.observableArrayList();
        while(resultSet.next()){
            plates.add(new Plate(resultSet.getInt("id"), resultSet.getString("plate"), resultSet.getString("owner_full_name"),
                    resultSet.getString("owner_address"), resultSet.getInt("brand_id"), resultSet.getDate("release_date"),
                    resultSet.getFloat("engine_capacity"), resultSet.getString("engine_number"), resultSet.getString("chassis_number"),
                    resultSet.getString("body_number"), resultSet.getString("color"), resultSet.getInt("for_organization"),
                    resultSet.getInt("organization_id"), resultSet.getDate("addition_date")));
        }

        return plates;
    }

    public boolean isSatisfiedBySeries(String series){
        String realSeries = plate.replaceAll("[^A-Za-z]", "");
        return realSeries.equals(series);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getOwner_full_name() {
        return owner_full_name;
    }

    public void setOwner_full_name(String owner_full_name) {
        this.owner_full_name = owner_full_name;
    }

    public String getOwner_address() {
        return owner_address;
    }

    public void setOwner_address(String owner_address) {
        this.owner_address = owner_address;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public float getEngine_capacity() {
        return engine_capacity;
    }

    public void setEngine_capacity(float engine_capacity) {
        this.engine_capacity = engine_capacity;
    }

    public String getEngine_number() {
        return engine_number;
    }

    public void setEngine_number(String engine_number) {
        this.engine_number = engine_number;
    }

    public String getChassis_number() {
        return chassis_number;
    }

    public void setChassis_number(String chassis_number) {
        this.chassis_number = chassis_number;
    }

    public String getBody_number() {
        return body_number;
    }

    public void setBody_number(String body_number) {
        this.body_number = body_number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFor_organization() {
        return for_organization;
    }

    public void setFor_organization(int for_organization) {
        this.for_organization = for_organization;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public Date getAddition_date() {
        return addition_date;
    }

    public void setAddition_date(Date addition_date) {
        this.addition_date = addition_date;
    }
}
