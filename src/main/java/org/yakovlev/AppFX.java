package org.yakovlev;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.yakovlev.controllers.*;

import java.sql.Connection;

public class AppFX extends Application {

    private OracleConnector connector;
    private Stage primaryStage;
    private ObservableList<String> tables;
    private ObservableList<Integer> filters;

    public void main() {
        Application.launch();
    }

    public Stage getStage(){
        return primaryStage;
    }

    public ObservableList<String> getTables() {
        return tables;
    }

    public void setTables(ObservableList<String> tables) {
        this.tables = tables;
    }

    public ObservableList<Integer> getFilters() {
        return filters;
    }

    public void setFilters(ObservableList<Integer> filters) {
        this.filters = filters;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        connector = new OracleConnector();

        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/loginScene.fxml"));
        Parent loginScene = loader.load();
        this.primaryStage.setScene(new Scene(loginScene));
        this.primaryStage.show();

        LoginController controller = loader.getController();
        controller.setApp(this);


//
//        Parent selectModeScene = FXMLLoader.load(getClass().getClassLoader().getResource("controllers/selectModeScene.fxml"));
//
//
//        primaryStage.setScene(new Scene(selectModeScene));
//        primaryStage.show();
    }

    public boolean establishConnection(String ip, String port, String sid, String login, String pass){
        return connector.connect(ip, port, sid, login, pass);
    }

    public void showError(String error) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/error.fxml"));

        Parent page = loader.load();
        Label error_text = (Label) page.lookup("#error_text");
        error_text.setText(error);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Error");
        dialogStage.initOwner(primaryStage);
        dialogStage.setScene(new Scene(page));
        dialogStage.show();

        ErrorController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
    }

    public void showSelectRoleWindow() throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/rolesModeScene.fxml"));
        Parent rolesScene = loader.load();
        this.primaryStage.setScene(new Scene(rolesScene));
        this.primaryStage.show();

        RolesModeController controller = loader.getController();
        controller.setApp(this);
    }

    public void showSelectModeWindow() throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/selectModeScene.fxml"));
        Parent loginScene = loader.load();
        this.primaryStage.setScene(new Scene(loginScene));
        this.primaryStage.show();

        SelectModeController controller = loader.getController();
        controller.setApp(this);
    }

    public void showFiltrationModeWindow() throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/filterModeScene.fxml"));
        Parent loginScene = loader.load();
        this.primaryStage.setScene(new Scene(loginScene));
        this.primaryStage.show();

        FilterModeController controller = loader.getController();
        controller.setApp(this);
        controller.setFilters(filters);
    }

    public void showTablesModeWindow() throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/tablesModeScene.fxml"));


        Parent loginScene = loader.load();
        this.primaryStage.setScene(new Scene(loginScene));
        this.primaryStage.show();

        TablesModeController controller = loader.getController();
        controller.setApp(this);
        controller.setTables_list();
    }

    public Connection getConnection(){
        return connector.getConnection();
    }
}
