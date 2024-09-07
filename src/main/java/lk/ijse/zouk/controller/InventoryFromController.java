package lk.ijse.zouk.controller;


import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.zouk.RegExPatterns;
import lk.ijse.zouk.dto.InventoryDto;
import lk.ijse.zouk.dto.InventorySupplierDto;
import lk.ijse.zouk.dto.SupplierDto;
import lk.ijse.zouk.model.InventoryModel;
import lk.ijse.zouk.model.SupplierModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InventoryFromController {
    @FXML
    private JFXButton BtninventoryDelete;

    @FXML
    private TableView<SupplierDto> InventoryTable;

    @FXML
    private AnchorPane Pageinventory;

    @FXML
    private TableColumn<?, ?> TableDescription;

    @FXML
    private TableColumn<?, ?> TableInventoryId;

    @FXML
    private TableColumn<?, ?> TablePrice;

    @FXML
    private TableColumn<?, ?> TableQty;

    @FXML
    private TableColumn<?, ?> TableSupId;

    @FXML
    private TableColumn<?, ?> TableSupName;

    @FXML
    private TableColumn<?, ?> TableSupcontact;

    @FXML
    private TableColumn<?, ?> TableType;

    @FXML
    private TextField TxtDescription;

    @FXML
    private TextField TxtInventorySearch;

    @FXML
    private TextField TxtPrice;

    @FXML
    private TextField TxtQty;

    @FXML
    private TextField TxtSupContact;

    @FXML
    private TextField TxtSupName;

    @FXML
    private TextField TxtType;

    @FXML
    private Label InventoryIDLabel;

    @FXML
    private Label SupplierIDLabel;

    @FXML
    private TableView<SupplierDto> SupplierTable;

    @FXML
    private TableColumn<?, ?> TableColumnSupID;

    @FXML
    private TableColumn<?, ?> tableColumnSupName;

    @FXML
    private TableColumn<?, ?> tableCoumnSupContact;


    private InventoryModel inventoryModel = new InventoryModel();
    private SupplierModel supplierModel = new SupplierModel();

    public void initialize() {
        setCellValueFactory();
        setCellValueFactory2();
        loadAllInvntory();
        loadAllSupplier();
        generateNextInventoryID();
        generateNextSupplierID();
    }

    private void  generateNextSupplierID(){
        try {
            String previousSupplierID = SupplierIDLabel.getText();
            String supID = supplierModel.generateNextSupplier();
            SupplierIDLabel.setText(supID);
            clearFields();
            if (btnClearPressed){
                SupplierIDLabel.setText(previousSupplierID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void  generateNextInventoryID(){
        try {
            String previousInventoryID = InventoryIDLabel.getText();
            String inventoryID = inventoryModel.generateNextInventoryID();
            InventoryIDLabel.setText(inventoryID);
            clearFields();
            if (btnClearPressed){
                InventoryIDLabel.setText(previousInventoryID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private boolean btnClearPressed = false;
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextInventoryID();
        generateNextSupplierID();
    }

    private void setCellValueFactory() {
        TableInventoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    private void setCellValueFactory2(){
        TableColumnSupID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnSupName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCoumnSupContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void loadAllInvntory() {
        var inventoryModel1 = new InventoryModel();

        ObservableList<SupplierDto> obList = FXCollections.observableArrayList();

        try {
            List<InventoryDto> inventoryDtos = inventoryModel1.getAllInventory();

            for (InventoryDto dto : inventoryDtos) {
                obList.add(
                        new InventoryDto(
                                dto.getId(),
                                dto.getType(),
                                dto.getPrice(),
                                dto.getDescription(),
                                dto.getQuantity()
                        )
                );
            }
            InventoryTable.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadAllSupplier() {
        var supplierModel1 = new SupplierModel();

        ObservableList<SupplierDto> obList1 = FXCollections.observableArrayList();

        try {
            List<SupplierDto> supplierDtos = supplierModel1.getAllSupplier();

            for (SupplierDto dto : supplierDtos) {
                obList1.add(
                        new SupplierDto(
                                dto.getId(),
                                dto.getName(),
                                dto.getContact()
                        )
                );
            }
            SupplierTable.setItems(obList1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        String id = TxtInventorySearch.getText();

        try {
            boolean isDeleted = inventoryModel.deleteInventory(id);
            boolean isDeleted1 = supplierModel.deleteSupplier(id);
            if(isDeleted || isDeleted1) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory has been deleted!").show();
                clearFields();
                generateNextInventoryID();
                generateNextSupplierID();
                loadAllSupplier();
                loadAllInvntory();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory Id not matched!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void BtnInventorySaveOnAction(ActionEvent event) {
        String inventoryId = InventoryIDLabel.getText();
        String type = TxtType.getText();
        double price = Double.parseDouble(TxtPrice.getText());
        String description = TxtDescription.getText();
        int quantity = Integer.parseInt(TxtQty.getText());
        String supId = SupplierIDLabel.getText();
        String supName = TxtSupName.getText();
        int supcontact = Integer.parseInt(TxtSupContact.getText());

        boolean isValidName = RegExPatterns.getValidName().matcher(supName).matches();
        boolean isvalidDesc = RegExPatterns.getValidDescription().matcher(description).matches();
        boolean isValidType = RegExPatterns.getValidType().matcher(type).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a valid SupplierName").showAndWait();
            return;
        }if (!isvalidDesc) {
            new Alert(Alert.AlertType.ERROR, "Not a Valid Description").showAndWait();
            return;
        }if (!isValidType){
            new Alert(Alert.AlertType.ERROR, "Not a Valid Type").showAndWait();
        }else {

            var inventoryDto = new InventoryDto(inventoryId, type, price, description, quantity);
            var supplierDto = new SupplierDto(supId, supName, supcontact);
            var inventorySupplierDto = new InventorySupplierDto(inventoryId, supId);
            try {
                boolean isSaved1 = inventoryModel.saveInventory(inventoryDto);
                boolean isSaved2 = supplierModel.saveSupplier(supplierDto);
                boolean isSaved3 = inventoryModel.saveinventorySupplierDetails(inventorySupplierDto);

                if (isSaved1 && isSaved2 && isSaved3) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Inventory has been saved!").show();
                    clearFields();
                    generateNextSupplierID();
                    generateNextInventoryID();
                    loadAllInvntory();
                    loadAllSupplier();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void clearFields() {
        TxtType.setText("");
        TxtPrice.setText("");
        TxtDescription.setText("");
        TxtQty.setText("");
        TxtSupName.setText("");
        TxtSupContact.setText("");
        TxtInventorySearch.setText("");

    }

    @FXML
    void BtnInventoryUpdateOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/updateInventory_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void txtGoToPrice(ActionEvent event) {
        TxtPrice.requestFocus();
    }

    @FXML
    void txtGoToQty(ActionEvent event) {
        TxtQty.requestFocus();
    }

    @FXML
    void txtGoToSave(ActionEvent event) {
        BtnInventorySaveOnAction(new ActionEvent());
    }

    @FXML
    void txtGoToSupContact(ActionEvent event) {
        TxtSupContact.requestFocus();
    }

    @FXML
    void txtGoToSupName(ActionEvent event) {
        TxtSupName.requestFocus();
    }

    @FXML
    void txtxGoToDescription(ActionEvent event) {
        TxtDescription.requestFocus();
    }
}
