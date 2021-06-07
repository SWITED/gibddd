package org.yakovlev.controllers.filters;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.yakovlev.database.Organization;
import org.yakovlev.database.Plate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OwnerInformationController extends FilterController{

    @FXML
    private TextField plate;
    @FXML
    private TableView mainTable;

    public static String getLabelText(){return "2. Сведения о владельце автотранспортного средства";}
    public static String getFXML(){return "ownerInformation.fxml";}
    public static String getTitle(){return "Сведения о владельце автотранспортного средства";}

    public void filter() throws Exception {

        String plateValue = plate.getText();
        if(!plateValue.isEmpty()) {
            Connection conn = app.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM plates WHERE plate = ?");
            ps.setString(1, plateValue);
            ps.execute();
            ResultSet resultSet = ps.getResultSet();

            ObservableList<Plate> plates = Plate.createArrayFromResultSet(resultSet);

            mainTable.getColumns().clear();

            TableColumn<Plate, String> ownerFullNameColumn = new TableColumn<Plate, String>("owner_full_name");
            ownerFullNameColumn.setCellValueFactory(new PropertyValueFactory<Plate, String>("owner_full_name"));
            mainTable.getColumns().add(ownerFullNameColumn);

            TableColumn<Plate, String> ownerAddressColumn = new TableColumn<Plate, String>("owner_address");
            ownerAddressColumn.setCellValueFactory(new PropertyValueFactory<Plate, String>("owner_address"));
            mainTable.getColumns().add(ownerAddressColumn);

            TableColumn<Plate, Integer> forOrganizationColumn = new TableColumn<Plate, Integer>("for_organization");
            forOrganizationColumn.setCellValueFactory(new PropertyValueFactory<Plate, Integer>("for_organization"));
            mainTable.getColumns().add(forOrganizationColumn);

            TableColumn<Plate, Integer> organizationIdColumn = new TableColumn<Plate, Integer>("organization_id");
            organizationIdColumn.setCellValueFactory(new PropertyValueFactory<Plate, Integer>("organization_id"));
            mainTable.getColumns().add(organizationIdColumn);


            mainTable.getItems().removeAll();
            mainTable.setItems(plates);
            mainTable.refresh();

        } else {
            app.showError("Пустое поле!");
        }
    }
}
