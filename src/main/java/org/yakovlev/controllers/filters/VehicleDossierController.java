package org.yakovlev.controllers.filters;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.yakovlev.database.Plate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VehicleDossierController extends FilterController{

    @FXML
    private TextField plate;
    @FXML
    private TableView mainTable;

    public static String getLabelText(){return "3. \"Досье\" на автомобиль по государственному номеру";}
    public static String getFXML(){return "vehicleDossier.fxml";}
    public static String getTitle(){return "\"Досье\" на автомобиль по государственному номеру";}

    public void filter() throws Exception {

        String plateValue = plate.getText();
        if(!plateValue.isEmpty()) {

            Connection conn = app.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT engine_number, body_number, chassis_number, is_passed " +
                    "FROM plates INNER JOIN technical_inspections ti1 on plates.id = ti1.plate_id WHERE plate = ?");
            ps.setString(1, plateValue);
            ps.execute();
            ResultSet resultSet = ps.getResultSet();


            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            mainTable.getColumns().clear();

            for(int i=0 ; i < resultSet.getMetaData().getColumnCount(); i++){
                final int j = i;

                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i+1));

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
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

            mainTable.setItems(data);

        } else {
            app.showError("Пустое поле!");
        }


    }
}
