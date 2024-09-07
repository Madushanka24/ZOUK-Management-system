package lk.ijse.zouk.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.zouk.RegExPatterns;
import lk.ijse.zouk.dto.InventoryDto;
import lk.ijse.zouk.dto.StaffDto;
import lk.ijse.zouk.dto.SupplierDto;
import lk.ijse.zouk.model.InventoryModel;
import lk.ijse.zouk.model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class UpdateInventoryFromController {

    @FXML
    private AnchorPane InventoryUpdatePage;

    @FXML
    private TextField TxtUpdateDescription;

    @FXML
    private TextField TxtUpdateInventoryID;

    @FXML
    private TextField TxtUpdatePrice;

    @FXML
    private TextField TxtUpdateQty;

    @FXML
    private TextField TxtUpdateSupContact;

    @FXML
    private TextField TxtUpdateSupId;

    @FXML
    private TextField TxtUpdateSupName;

    @FXML
    private TextField TxtUpdateType;

    private InventoryModel inventoryModel = new InventoryModel();
    private SupplierModel supplierModel = new SupplierModel();

    private InventoryFromController inventoryFromController = new InventoryFromController();
    @FXML
    void BtnInventoryUpdatePageCancelOnAction(ActionEvent event) {
        InventoryUpdatePage.getScene().getWindow().hide();
    }

    public void initialize() {
        loadAllInvntory();
        loadAllSupplier();
    }

    @FXML
    void BtnInventoryfUpdatePageUpdateOnAction(ActionEvent event) {
        String id = TxtUpdateInventoryID.getText();
        String type = TxtUpdateType.getText();
        String desc = TxtUpdateDescription.getText();
        int qty = Integer.parseInt(TxtUpdateQty.getText());
        double price = Double.parseDouble(TxtUpdatePrice.getText());
        String supId = TxtUpdateSupId.getText();
        String supName = TxtUpdateSupName.getText();
        int supContact = Integer.parseInt(TxtUpdateSupContact.getText());

        boolean isValidName = RegExPatterns.getValidName().matcher(supName).matches();
        boolean isvalidDesc = RegExPatterns.getValidDescription().matcher(desc).matches();
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

            var dto = new InventoryDto(id, type, price, desc, qty);
            var dto1 = new SupplierDto(supId, supName, supContact);

            try {
                boolean isUpdated = inventoryModel.updateInventory(dto);
                boolean isUpdated1 = supplierModel.updateSupplier(dto1);
                if (isUpdated && isUpdated1) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Inventory has been updated!").show();
                    clearFields();

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
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
            inventoryFromController.getInventoryTable().setItems(obList);
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
            inventoryFromController.getSupplierTable().setItems(obList1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        TxtUpdateInventoryID.setText("");
        TxtUpdateType.setText("");
        TxtUpdateDescription.setText("");
        TxtUpdateQty.setText("");
        TxtUpdatePrice.setText("");
        TxtUpdateSupId.setText("");
        TxtUpdateSupName.setText("");
        TxtUpdateSupContact.setText("");
    }


}
