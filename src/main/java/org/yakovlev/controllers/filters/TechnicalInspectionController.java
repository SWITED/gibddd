package org.yakovlev.controllers.filters;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TechnicalInspectionController extends FilterController{

    @FXML
    private Label size;
    @FXML
    private TableView mainTable;

    public static String getLabelText(){return "4. Перечень и общее число владельцев машин не прошедших вовремя техосмотр";}
    public static String getFXML(){return "technicalInspection.fxml";}
    public static String getTitle(){return "Перечень и общее число владельцев машин не прошедших вовремя техосмотр";}

    public void filter() throws Exception {
        Connection conn = app.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement ps = conn.prepareStatement("SELECT owner_full_name, plate FROM plates INNER JOIN technical_inspections ti1 on plates.id = ti1.plate_id WHERE is_passed = 0");
        ps.execute();
        ResultSet resultSet = ps.getResultSet();


        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        mainTable.getColumns().clear();

        for(int i=0 ; i < resultSet.getMetaData().getColumnCount(); i++){
            final int j = i;

            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i+1));

            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            mainTable.getColumns().addAll(col);
        }

        while(resultSet.next()){
            ObservableList<String> row = FXCollections.observableArrayList();

            for(int i = 1 ; i <= resultSet.getMetaData().getColumnCount(); i++){
                row.add(resultSet.getString(i));
            }
            data.add(row);
        }

        PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(owner_full_name) AS ofnSize " +
                "FROM (SELECT DISTINCT owner_full_name FROM plates " +
                "INNER JOIN technical_inspections ti1 on plates.id = ti1.plate_id WHERE is_passed = 0)");

        ps1.execute();
        ResultSet resultSet1 = ps1.getResultSet();
        resultSet1.next();
        size.setText("Общее число владельцев: " + resultSet1.getInt("ofnSize"));

        conn.commit();
        mainTable.setItems(data);
    }
}
