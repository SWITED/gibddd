package org.yakovlev.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.yakovlev.AppFX;
import org.yakovlev.controllers.filters.FilterController;
import org.yakovlev.controllers.filters.FiltersEnum;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FilterModeController {

    private AppFX app;
    @FXML
    private GridPane FiltersContainer;

    public void setApp(AppFX app){

        this.app = app;
    }

    private Class<FilterController> getClass(int i, int j) throws ClassNotFoundException {
        String className = FiltersEnum.values()[i * 4 + j].toString();
        return (Class<FilterController>)Class.forName("org.yakovlev.controllers.filters." + className + "Controller");
    }

    public void setFilters(ObservableList<Integer> filters) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        FiltersContainer.getChildren().clear();

        int i = 0, j = 0;
        for(Integer f: filters){
            if(j == 4) {
                i++;
                j = 0;
            }

            Class<FilterController> clazz = getClass(i, j);
            String labelText = (String)clazz.getDeclaredMethod("getLabelText").invoke(null);
            String fileFXML = (String)clazz.getDeclaredMethod("getFXML").invoke(null);
            String title = (String)clazz.getDeclaredMethod("getTitle").invoke(null);

            GridPane child = new GridPane();
            ColumnConstraints c = new ColumnConstraints();
            c.setHalignment(HPos.CENTER);
            c.setMinWidth(150.0);
            c.setPrefWidth(150.0);
            c.setHgrow(Priority.SOMETIMES);
            child.getColumnConstraints().add(c);

            RowConstraints r1 = new RowConstraints();
            r1.setMinHeight(75.0);
            r1.setPrefHeight(75.0);
            child.getRowConstraints().add(r1);

            RowConstraints r2 = new RowConstraints();
            r2.setMinHeight(25.0);
            r2.setPrefHeight(25.0);
            child.getRowConstraints().add(r2);

            Label childLabel = new Label(labelText);
            childLabel.setTextAlignment(TextAlignment.CENTER);
            childLabel.setWrapText(true);
            child.add(childLabel, 0, 0);

            Button childButton = new Button("Фильтровать");
            childButton.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent event) {

                    try {
                        openNewWindow(fileFXML, title);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            child.add(childButton, 0, 1);

            FiltersContainer.add(child, j, i);

            j++;
        }


    }

    private void openNewWindow(String templateName, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/filters/" + templateName));

        Parent page = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle(title);
        dialogStage.initOwner(app.getStage());
        dialogStage.setScene(new Scene(page));
        dialogStage.show();

        FilterController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setApp(app);

        dialogStage.showAndWait();
    }

//    @FXML
//    private void button1() throws Exception {
//        openNewWindow("organizations.fxml", "Перечень и общее число организаций, которым выделены номера");
//    }
//
//    @FXML
//    private void button2() throws Exception {
//        openNewWindow("ownerInformation.fxml", "Сведения о владельце автотранспортного средства");
//    }
//
//    @FXML
//    private void button3() throws Exception {
//        openNewWindow("vehicleDossier.fxml", "\"Досье\" на автомобиль по государственному номеру");
//    }
//
//    @FXML
//    private void button4() throws Exception {
//        openNewWindow("technicalInspection.fxml", "Перечень и общее число владельцев машин не прошедших вовремя техосмотр");
//    }

//    @FXML
//    private void button5() throws Exception {
//        openNewWindow("trafficAccidentStatistics.fxml", "Статистика по любому типу ДТП");
//    }
//
//    @FXML
//    private void button6() throws Exception {
//        openNewWindow("accidentAnalysis.fxml", "Результаты анализа ДТП");
//    }
//
//    @FXML
//    private void button7() throws Exception {
//        openNewWindow("accidentsNumber.fxml", "Данные о количестве ДТП");
//    }
//
//    @FXML
//    private void button8() throws Exception {
//        openNewWindow("wantedList.fxml", "Список машин, отданных в розыск");
//    }

//    @FXML
//    private void button9() throws Exception {
//        openNewWindow("investigativeWorkEfficiency.fxml", "Данные об эффективности розыскной работы");
//    }
//
//    @FXML
//    private void button10() throws Exception {
//        openNewWindow("hijackingsNumber.fxml", "Перечень и общее число угонов");
//    }
//
//    @FXML
//    private void button11() throws Exception {
//        openNewWindow("hijackingsStatistics.fxml", "Статистика по угонам");
//    }
}
