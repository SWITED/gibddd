package org.yakovlev.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.yakovlev.AppFX;
import org.yakovlev.database.types.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AddController {

    private AppFX app;
    private TablesModeController tablesModeController;
    private Stage dialogStage;
    private String selectedTable;

    @FXML
    private GridPane tableGrid;

    public void setApp(AppFX app){
        this.app = app;
    }

    public void setTablesModeController(TablesModeController tablesModeController){
        this.tablesModeController = tablesModeController;
    }

    public void setSelectedTable(String selectedTable){
        this.selectedTable = selectedTable;
    }

    public GridPane getTableGrid() {
        return tableGrid;
    }

    public void setDialogStage(Stage ds){
        dialogStage = ds;
    }

    @FXML
    private void addRecord() throws SQLException, ClassNotFoundException, NoSuchFieldException, ParseException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ObservableList<Node> nodes = tableGrid.getChildren();
        System.out.println(nodes);

        Class<?> clazz = tablesModeController.getTableClassFromTableName(selectedTable);
        ArrayList<DatabaseType> fields = tablesModeController.getTableFields(clazz);
        String[] fieldNames = new String[fields.size()];

        int j = 0;
        for(DatabaseType f: fields){
            fieldNames[j] = f.getName();
            j++;
        }

        Connection conn = app.getConnection();
        conn.setAutoCommit(false);
        String[] fieldValues = new String[fields.size()];

        j = 0;
        for(Node n: nodes){
          if(!(n instanceof Label)){
              TextField tf = (TextField)n;
              if(tf.getId().equals("id")){
                  PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) as maxId FROM " + selectedTable);
                  ps.execute();
                  ResultSet resultSet = ps.getResultSet();
                  resultSet.next();
                  fieldValues[j] = Integer.toString(resultSet.getInt("maxId") + 1);
              } else {
                  fieldValues[j] = tf.getText();
              }
              j++;
          }
        }


        String q = "";
        for(int i = 0; i < fieldNames.length-1; i++) q = q.concat(",?");
        PreparedStatement ps = conn.prepareStatement("INSERT INTO " + selectedTable + " VALUES (?" + q + ")", fieldNames);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        j = 0;
        for(DatabaseType f: fields){
            Type t = clazz.getDeclaredField(f.getName()).getType(); //clazz.getName() + "." +

            switch (t.getTypeName()){
                case "int":
                    ps.setInt(j+1, Integer.parseInt(fieldValues[j]));
                    break;
                case "float":
                    ps.setFloat(j+1, Float.parseFloat(fieldValues[j]));
                    break;
                case "java.lang.String":
                    ps.setString(j+1, fieldValues[j]);
                    break;
                case "java.util.Date":
                    ps.setDate(j+1, new java.sql.Date (format.parse(fieldValues[j]).getTime()));
                    break;
                default:
                    ps.setInt(j+1, Integer.parseInt(fieldValues[j]));
            }

            j++;
        }

        ps.execute();
        conn.commit();
        tablesModeController.showTable(selectedTable);
        dialogStage.close();
    }
}
