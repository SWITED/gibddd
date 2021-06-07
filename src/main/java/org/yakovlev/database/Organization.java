package org.yakovlev.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Organization extends TableClass{

    private int id;
    private String name;
    private String address;
    private String dir_full_name;

    public Organization(int id, String name, String address, String dir_full_name){
        this.id = id;
        this.name = name;
        this.address = address;
        this.dir_full_name = dir_full_name;
    }

    public static ObservableList<Organization> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<Organization> organizations = FXCollections.observableArrayList();
        while(resultSet.next()){
            organizations.add(new Organization(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("dir_full_name")));
        }

        return organizations;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDir_full_name() {
        return dir_full_name;
    }

    public void setDir_full_name(String dir_full_name) {
        this.dir_full_name = dir_full_name;
    }
}
