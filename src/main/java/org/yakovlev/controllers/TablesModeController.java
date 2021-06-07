package org.yakovlev.controllers;

//import com.sun.xml.ws.util.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.yakovlev.AppFX;
import org.yakovlev.database.CarBrand;
import org.yakovlev.database.Organization;
import org.yakovlev.database.Plate;
import org.yakovlev.database.types.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class TablesModeController implements Initializable {

    private AppFX app;
    private String selectedTable;
    private Object selectedRecord;

    @FXML
    private ListView tables_list;
    @FXML
    private TableView main_table;


    public void setApp(AppFX app){
        this.app = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        selectedTable = "";
    }

    public void setTables_list() {
        tables_list.setItems(app.getTables());
    }

    public Object getSelectedRecord(){
        return selectedRecord;
    }

    public void showTable(String tableName) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {

        Connection conn = app.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName);
        ps.execute();
        ResultSet resultSet = ps.getResultSet();

        Class<?> clazz = getTableClassFromTableName(tableName);

        Class[] paramResultSet = new Class[1];
        paramResultSet[0] = ResultSet.class;

        Method method = clazz.getMethod("createArrayFromResultSet", paramResultSet);
        ObservableList<?> objects = (ObservableList<?>)method.invoke(null, resultSet);

        main_table.getColumns().clear();

        Field[] fields = clazz.getDeclaredFields();
        for(Field f: fields) {

            switch (f.getType().toString()){
                default:
                case "int":
                    TableColumn<Object, Integer> columnInt = new TableColumn<Object, Integer>(f.getName());
                    columnInt.setCellValueFactory(new PropertyValueFactory<Object, Integer>(f.getName()));
                    main_table.getColumns().add(columnInt);

                    break;
                case "float":
                    TableColumn<Object, Float> columnFloat = new TableColumn<Object, Float>(f.getName());
                    columnFloat.setCellValueFactory(new PropertyValueFactory<Object, Float>(f.getName()));
                    main_table.getColumns().add(columnFloat);

                    break;
                case "java.lang.String":
                    TableColumn<Object, String> columnString = new TableColumn<Object, String>(f.getName());
                    columnString.setCellValueFactory(new PropertyValueFactory<Object, String>(f.getName()));
                    main_table.getColumns().add(columnString);

                    break;
                case "java.util.Date":
                    TableColumn<Object, Date> columnDate = new TableColumn<Object, Date>(f.getName());
                    columnDate.setCellValueFactory(new PropertyValueFactory<Object, Date>(f.getName()));
                    main_table.getColumns().add(columnDate);

                    break;
            }

        }

        main_table.getItems().removeAll();
        main_table.setItems(objects);
        main_table.refresh();

//        switch (tableName){
//            default:
//            case ("plates"):
//                Class<?> clazz = Plate.class;
//                Class[] paramResultSet = new Class[1];
//                paramResultSet[0] = ResultSet.class;
//                Method method = clazz.getMethod("createPlatesArrayFromResultSet", paramResultSet);
//                ObservableList<?> plates = (ObservableList<?>)method.invoke(null, resultSet);
//
//                main_table.getColumns().clear();
//
//                Field[] fields = clazz.getDeclaredFields();
//                for(Field f: fields) {
//
//                    switch (f.getType().toString()){
//                        default:
//                        case "int":
//                            TableColumn<Object, Integer> columnInt = new TableColumn<Object, Integer>(f.getName());
//                            columnInt.setCellValueFactory(new PropertyValueFactory<Object, Integer>(f.getName()));
//                            main_table.getColumns().add(columnInt);
//
//                            break;
//                        case "float":
//                            TableColumn<Object, Float> columnFloat = new TableColumn<Object, Float>(f.getName());
//                            columnFloat.setCellValueFactory(new PropertyValueFactory<Object, Float>(f.getName()));
//                            main_table.getColumns().add(columnFloat);
//
//                            break;
//                        case "java.lang.String":
//                            TableColumn<Object, String> columnString = new TableColumn<Object, String>(f.getName());
//                            columnString.setCellValueFactory(new PropertyValueFactory<Object, String>(f.getName()));
//                            main_table.getColumns().add(columnString);
//
//                            break;
//                        case "java.util.Date":
//                            TableColumn<Object, Date> columnDate = new TableColumn<Object, Date>(f.getName());
//                            columnDate.setCellValueFactory(new PropertyValueFactory<Object, Date>(f.getName()));
//                            main_table.getColumns().add(columnDate);
//
//                            break;
//                    }
//
//                }
//
////                TableColumn<Plate, Integer> idColumnPlate = new TableColumn<Plate, Integer>("id");
////                idColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, Integer>("id"));
////                main_table.getColumns().add(idColumnPlate);
////
////                TableColumn<Plate, String> plateColumnPlate = new TableColumn<Plate, String>("plate");
////                plateColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, String>("plate"));
////                main_table.getColumns().add(plateColumnPlate);
////
////                TableColumn<Plate, String> ownerFullNameColumnPlate = new TableColumn<Plate, String>("owner_full_name");
////                ownerFullNameColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, String>("owner_full_name"));
////                main_table.getColumns().add(ownerFullNameColumnPlate);
////
////                TableColumn<Plate, String> ownerAddressColumnPlate = new TableColumn<Plate, String>("owner_address");
////                ownerAddressColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, String>("owner_address"));
////                main_table.getColumns().add(ownerAddressColumnPlate);
////
////                TableColumn<Plate, Integer> brandIdColumnPlate = new TableColumn<Plate, Integer>("brand_id");
////                brandIdColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, Integer>("brand_id"));
////                main_table.getColumns().add(brandIdColumnPlate);
////
////                TableColumn<Plate, Date> releaseDateColumnPlate = new TableColumn<Plate, Date>("release_date");
////                releaseDateColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, Date>("release_date"));
////                main_table.getColumns().add(releaseDateColumnPlate);
////
////                TableColumn<Plate, Float> engineCapacityColumnPlate = new TableColumn<Plate, Float>("engine_capacity");
////                engineCapacityColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, Float>("engine_capacity"));
////                main_table.getColumns().add(engineCapacityColumnPlate);
////
////                TableColumn<Plate, String> engineNumberColumnPlate = new TableColumn<Plate, String>("engine_number");
////                engineNumberColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, String>("engine_number"));
////                main_table.getColumns().add(engineNumberColumnPlate);
////
////                TableColumn<Plate, String> chassisNumberColumnPlate = new TableColumn<Plate, String>("chassis_number");
////                chassisNumberColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, String>("chassis_number"));
////                main_table.getColumns().add(chassisNumberColumnPlate);
////
////                TableColumn<Plate, String> bodyNumberColumnPlate = new TableColumn<Plate, String>("body_number");
////                bodyNumberColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, String>("body_number"));
////                main_table.getColumns().add(bodyNumberColumnPlate);
////
////                TableColumn<Plate, String> colorColumnPlate = new TableColumn<Plate, String>("color");
////                colorColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, String>("color"));
////                main_table.getColumns().add(colorColumnPlate);
////
////                TableColumn<Plate, Integer> forOrganizationColumnPlate = new TableColumn<Plate, Integer>("for_organization");
////                forOrganizationColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, Integer>("for_organization"));
////                main_table.getColumns().add(forOrganizationColumnPlate);
////
////                TableColumn<Plate, Integer> organizationIdColumnPlate = new TableColumn<Plate, Integer>("organization_id");
////                organizationIdColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, Integer>("organization_id"));
////                main_table.getColumns().add(organizationIdColumnPlate);
////
////                TableColumn<Plate, Date> additionDateColumnPlate = new TableColumn<Plate, Date>("addition_date");
////                additionDateColumnPlate.setCellValueFactory(new PropertyValueFactory<Plate, Date>("addition_date"));
////                main_table.getColumns().add(additionDateColumnPlate);
//
//                main_table.getItems().removeAll();
//                main_table.setItems(plates);
//                main_table.refresh();
//                break;
//
//            case("organizations"):
//
//                ObservableList<Organization> organizations = Organization.createArrayFromResultSet(resultSet);
//
//                main_table.getColumns().clear();
//
//                TableColumn<Organization, Integer> idOrgColumn = new TableColumn<Organization, Integer>("id");
//                idOrgColumn.setCellValueFactory(new PropertyValueFactory<Organization, Integer>("id"));
//                main_table.getColumns().add(idOrgColumn);
//
//                TableColumn<Organization, String> nameColumn = new TableColumn<Organization, String>("name");
//                nameColumn.setCellValueFactory(new PropertyValueFactory<Organization, String>("name"));
//                main_table.getColumns().add(nameColumn);
//
//                TableColumn<Organization, String> addressColumn = new TableColumn<Organization, String>("address");
//                addressColumn.setCellValueFactory(new PropertyValueFactory<Organization, String>("address"));
//                main_table.getColumns().add(addressColumn);
//
//                TableColumn<Organization, String> dirFullNameColumn = new TableColumn<Organization, String>("dir_full_name");
//                dirFullNameColumn.setCellValueFactory(new PropertyValueFactory<Organization, String>("dir_full_name"));
//                main_table.getColumns().add(dirFullNameColumn);
//
//                main_table.getItems().removeAll();
//                main_table.setItems(organizations);
//                main_table.refresh();
//
//                break;
//            case ("car_brands"):
//
//                ObservableList<CarBrand> carBrands = CarBrand.createArrayFromResultSet(resultSet);
//
//                main_table.getColumns().clear();
//
//                TableColumn<CarBrand, Integer> idColumn = new TableColumn<CarBrand, Integer>("id");
//                idColumn.setCellValueFactory(new PropertyValueFactory<CarBrand, Integer>("id"));
//                main_table.getColumns().add(idColumn);
//
//                TableColumn<CarBrand, String> brandNameColumn = new TableColumn<CarBrand, String>("brand_name");
//                brandNameColumn.setCellValueFactory(new PropertyValueFactory<CarBrand, String>("brand_name"));
//                main_table.getColumns().add(brandNameColumn);
//
//                TableColumn<CarBrand, String> alarmSystemColumn = new TableColumn<CarBrand, String>("alarm_system");
//                alarmSystemColumn.setCellValueFactory(new PropertyValueFactory<CarBrand, String>("alarm_system"));
//                main_table.getColumns().add(alarmSystemColumn);
//
//
////                System.out.println(carBrands);
//
//                main_table.getItems().removeAll();
//                main_table.setItems(carBrands);
//                main_table.refresh();
//
//                break;
//        }


    }

    @FXML
    private void selectTableForView() throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        selectedTable = (String) tables_list.getSelectionModel().getSelectedItem();
        showTable(selectedTable);
    }

    @FXML
    private void selectTableRecordForChanging() throws SQLException {
        selectedRecord = main_table.getSelectionModel().getSelectedItem();
    }

    public Class<?> getTableClassFromTableName(String tableName) throws ClassNotFoundException {
        String[] words = tableName.split("_");
        for (int i = 0; i < words.length; i++) words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);

        tableName = String.join("", words);
        tableName = tableName.substring(0, tableName.length()-1);

        return Class.forName("org.yakovlev.database." + tableName);
    }

    public ArrayList<DatabaseType> getTableFields(Class<?> clazz){
        Field[] fs = clazz.getDeclaredFields();
        ArrayList<DatabaseType> fields = new ArrayList<DatabaseType>();

        for (int i = 0; i < fs.length; i++) {
            DatabaseType dt;
            String fieldName = fs[i].getName();

            switch (fs[i].getType().getName()){
                case "int":
                    if(fieldName.equals("id")) dt = new DBPrimaryKey(fieldName);
                    else if (fieldName.contains("id")) dt = new DBForeignKey(fieldName);
                    else dt = new DBInt(fieldName);
                    break;
                case "float":
                    dt = new DBFloat(fieldName);
                    break;
                case "java.lang.String":
                    dt = new DBString(fieldName);
                    break;
                case "java.util.Date":
                    dt = new DBDate(fieldName);
                    break;
                default:
                    dt = new DBInt(fieldName);
            }

            fields.add(dt);
        }

        return fields;
    }

    public ArrayList<Object> getTableValues(ArrayList<DatabaseType> fields, Class<?> clazz, Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Object> objects = new ArrayList<>();

        for(DatabaseType f: fields){
            String methodName = f.getName();
            methodName = "get" +  methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

            Method method = clazz.getDeclaredMethod(methodName);
            objects.add(method.invoke(o));
        }

        return objects;
    }

    private void addFieldsOnGrid(GridPane tableGrid, ArrayList<DatabaseType> fields, ArrayList<Object> values){

        for (int i = 0; i < fields.size(); i++) {

            Label l = fields.get(i).getFXLabel();
            GridPane.setMargin(l, new Insets(0, 0, 10, 0));
            tableGrid.add(l, 0, i);

            TextField tf = fields.get(i).getFXTextField();
            if(values != null) tf.setText(values.get(i).toString());
            GridPane.setMargin(tf, new Insets(0, 0, 10, 0));
            tableGrid.add(tf, 1, i);

        }
    }

    @FXML
    private void buttonAdd() throws Exception {

        if(selectedTable.equals("")){
            app.showError("Выберите таблицу");
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/add.fxml"));

            Class<?> clazz = getTableClassFromTableName(selectedTable);
            ArrayList<DatabaseType> fields = getTableFields(clazz);

            Parent page = loader.load();

            GridPane tableGrid = ((AddController)loader.getController()).getTableGrid();
            addFieldsOnGrid(tableGrid, fields, null);



            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setTitle("Добавить");
            dialogStage.initOwner(app.getStage());
            dialogStage.setScene(new Scene(page));
            dialogStage.show();

            AddController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setApp(app);
            controller.setSelectedTable(selectedTable);
            controller.setTablesModeController(this);

            dialogStage.showAndWait();
        }
    }

    @FXML
    private void buttonUpdate() throws Exception {
        if(selectedTable.equals("")){
            app.showError("Выберите таблицу");
        } else {
            if(selectedRecord == null) {
                app.showError("Выберите запись");
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("views/update.fxml"));

                Class<?> clazz = getTableClassFromTableName(selectedTable);
                ArrayList<DatabaseType> fields = getTableFields(clazz);
                ArrayList<Object> values = getTableValues(fields, clazz, selectedRecord);

                Parent page = loader.load();

                GridPane tableGrid = ((UpdateController)loader.getController()).getTableGrid();
                addFieldsOnGrid(tableGrid, fields, values);



                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setTitle("Изменить");
                dialogStage.initOwner(app.getStage());
                dialogStage.setScene(new Scene(page));
                dialogStage.show();

                UpdateController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setApp(app);
                controller.setSelectedTable(selectedTable);
                controller.setTablesModeController(this);

                dialogStage.showAndWait();
            }
        }
    }

    @FXML
    private void buttonDelete() throws Exception {
        if(selectedTable.equals("")){
            app.showError("Выберите таблицу");
        } else {
            if(selectedRecord == null) {
                app.showError("Выберите запись");
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("views/delete.fxml"));

                Class<?> clazz = getTableClassFromTableName(selectedTable);
                ArrayList<DatabaseType> fields = getTableFields(clazz);
//                ArrayList<Object> values = getTableValues(fields, clazz, selectedRecord);

                Parent page = loader.load();

//                GridPane tableGrid = ((UpdateController)loader.getController()).getTableGrid();
//                addFieldsOnGrid(tableGrid, fields, values);


                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setTitle("Удалить");
                dialogStage.initOwner(app.getStage());
                dialogStage.setScene(new Scene(page));
                dialogStage.show();

                DeleteController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setApp(app);
                controller.setSelectedTable(selectedTable);
                controller.setTablesModeController(this);

                dialogStage.showAndWait();
            }

        }
    }
}
