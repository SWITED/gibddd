package org.yakovlev.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.yakovlev.AppFX;

public class RolesModeController {

    private AppFX app;

    public void setApp(AppFX app){
        this.app = app;
    }



    private void chooseRole(String role) throws Exception {

        ObservableList<String> tables;
        ObservableList<Integer> filters;
        switch (role){
            case "gibdd_admin":
                tables = FXCollections.observableArrayList("car_brands", "car_types", "free_plates", "organizations", "plates", "ta_affected_vehicles",
                        "ta_types", "technical_inspections", "traffic_accidents", "wanted_cars");
                filters = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
                break;
            case "gibdd_inspector":
                tables = FXCollections.observableArrayList("plates", "technical_inspections");
                filters = FXCollections.observableArrayList(3,4);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
        
        app.setTables(tables);
        app.setFilters(filters);
        
        app.showSelectModeWindow();
    }

    @FXML
    private void chooseAdmin() throws Exception {
        chooseRole("gibdd_admin");
    }

    @FXML
    private void chooseInspector() throws Exception {
        chooseRole("gibdd_inspector");
    }
}
