package org.yakovlev.controllers.filters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.yakovlev.database.Organization;
import org.yakovlev.database.Plate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrganizationsController extends FilterController{

    @FXML
    private TextField series;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private TableView mainTable;

    public static String getLabelText(){return "1. Число организаций, которым выделены номера";}
    public static String getFXML(){return "organizations.fxml";}
    public static String getTitle(){return "Перечень и общее число организаций, которым выделены номера";}

    private boolean checkSeries(String seriesValue){
        String realSeries = seriesValue.replaceAll("[^A-Za-z]", "");
        return realSeries.equals(seriesValue) && (realSeries.length() <= 4);
    }

    private boolean checkDate(String date){
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            format.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void filter() throws Exception {

        String seriesValue = series.getText();
        if(!seriesValue.isEmpty()){

            if(!checkSeries(seriesValue)){
                app.showError("Некорректная серия!");
            } else {
                Connection conn = app.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT * FROM Plates WHERE id != 0");
                ps.execute();
                ResultSet resultSet = ps.getResultSet();

                ObservableList<Plate> plates = Plate.createArrayFromResultSet(resultSet);

                ArrayList<Integer> ids = new ArrayList<Integer>();
                for (Plate p: plates) {
                    if(p.isSatisfiedBySeries(seriesValue)) ids.add(p.getOrganization_id());
                }


                PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Organizations");
                ps1.execute();
                ResultSet resultSet1 = ps1.getResultSet();
                ObservableList<Organization> organizations = Organization.createArrayFromResultSet(resultSet1);

                ObservableList<Organization> validOrganizations = FXCollections.observableArrayList();
                for (Organization o: organizations) if(ids.contains(o.getId())) validOrganizations.add(o);

                mainTable.getColumns().clear();

                TableColumn<Organization, String> nameColumn = new TableColumn<Organization, String>("name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<Organization, String>("name"));
                mainTable.getColumns().add(nameColumn);

                mainTable.getItems().removeAll();
                mainTable.setItems(validOrganizations);
                mainTable.refresh();
            }

        } else {
            if(!startDate.getText().isEmpty() || !endDate.getText().isEmpty()){

                java.sql.Date startDateValue = new java.sql.Date(0);
                java.sql.Date endDateValue = new java.sql.Date(Long.MAX_VALUE);
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

                String startDateS = startDate.getText();
                String endDateS = endDate.getText();
                if(!startDateS.isEmpty()) if(checkDate(startDateS)) startDateValue = new java.sql.Date(format.parse(startDateS).getTime());
                if(!endDateS.isEmpty()) if(checkDate(endDateS)) endDateValue = new java.sql.Date(format.parse(endDateS).getTime());

                Connection conn = app.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT * FROM Plates WHERE id != 0 AND release_date >= ? AND release_date <= ?");
                ps.setDate(1, startDateValue);
                ps.setDate(2, endDateValue);
                ps.execute();
                ResultSet resultSet = ps.getResultSet();

                ObservableList<Plate> plates = Plate.createArrayFromResultSet(resultSet);

                ArrayList<Integer> ids = new ArrayList<Integer>();
                for (Plate p: plates) ids.add(p.getOrganization_id());

                PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Organizations");
                ps1.execute();
                ResultSet resultSet1 = ps1.getResultSet();
                ObservableList<Organization> organizations = Organization.createArrayFromResultSet(resultSet1);

                ObservableList<Organization> validOrganizations = FXCollections.observableArrayList();
                for (Organization o: organizations) if(ids.contains(o.getId())) validOrganizations.add(o);

                mainTable.getColumns().clear();

                TableColumn<Organization, String> nameColumn = new TableColumn<Organization, String>("name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<Organization, String>("name"));
                mainTable.getColumns().add(nameColumn);

                mainTable.getItems().removeAll();
                mainTable.setItems(validOrganizations);
                mainTable.refresh();
            } else {
                app.showError("Все поля пустые!");
            }
        }
    }
}
