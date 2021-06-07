package org.yakovlev.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.yakovlev.AppFX;
import org.yakovlev.OracleConnector;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private AppFX app;

    @FXML
    private TextField ip_text;
    @FXML
    private TextField port_text;
    @FXML
    private TextField sid_text;
    @FXML
    private TextField login_text;
    @FXML
    private TextField pass_text;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        System.out.println();
    }

    public void setApp(AppFX app){
        this.app = app;
    }

    private boolean signUp(String ip, String port, String sid, String login, String pass){
        return app.establishConnection(ip, port, sid, login, pass);
    }



    @FXML
    private void buttonDefaultSettings() throws Exception {
        if(signUp("84.237.50.81", "1521", "XE", "18205_yakovlev", "123456")) {
            app.showSelectRoleWindow();
        } else {
            app.showError("Не могу подключиться");
        }
    }

    @FXML
    private void buttonOtherSettings() throws Exception{
        if(signUp(ip_text.getText(), port_text.getText(), sid_text.getText(), login_text.getText(), pass_text.getText())) {
            app.showSelectRoleWindow();
        } else {
            app.showError("Не могу подключиться");
        }
    }


}

