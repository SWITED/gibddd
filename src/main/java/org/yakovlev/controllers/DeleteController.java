package org.yakovlev.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.yakovlev.AppFX;
import org.yakovlev.database.types.DatabaseType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DeleteController {

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
    private void deleteRecord() throws Exception {

        Class<?> clazz = tablesModeController.getTableClassFromTableName(selectedTable);
        Object o = tablesModeController.getSelectedRecord();
        int id = (int)clazz.getMethod("getId").invoke(o);

        Connection conn = app.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM " + selectedTable + " WHERE id=?");
        ps.setInt(1, id);
        try {
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e){
            app.showError("Не могу удалить запись.\nНарушена целостность.");
        }


        tablesModeController.showTable(selectedTable);
        dialogStage.close();
    }

    @FXML
    private void cancel() {
        dialogStage.close();
    }
}
