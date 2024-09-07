package lk.ijse.zouk.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.zouk.RegExPatterns;
import lk.ijse.zouk.dto.StaffDto;
import lk.ijse.zouk.model.StaffModel;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateStaffFromController {


    @FXML
    private AnchorPane PageUpdateStaff;

    @FXML
    private TextField TxtUpdateAddress;

    @FXML
    private TextField TxtUpdateContact;

    @FXML
    private TextField TxtUpdateId;

    @FXML
    private TextField TxtUpdateName;

    @FXML
    private TextField TxtUpdatePosition;

    @FXML
    private TextField TxtUpdateSalary;

    private StaffModel staffModel = new StaffModel();
    private StaffFromController staffFromController = new StaffFromController();

    @FXML
    void BtnStaffUpdatePageCancelOnAction(ActionEvent event) throws IOException {
        PageUpdateStaff.getScene().getWindow().hide();
    }

    @FXML
    void BtnStaffUpdatePageUpdateOnAction(ActionEvent event) {

        String id = TxtUpdateId.getText();
        String name = TxtUpdateName.getText();
        String address = TxtUpdateAddress.getText();
        int contact = Integer.parseInt(TxtUpdateContact.getText());
        String position = TxtUpdatePosition.getText();
        double salary = Double.parseDouble(TxtUpdateSalary.getText());

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidAddress = RegExPatterns.getValidAddress().matcher(address).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a valid Name").showAndWait();
            return;
        }if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Address").showAndWait();
            return;
        }else {

            var dto = new StaffDto(id, name, address, contact, position, salary);

            try {
                boolean isUpdated = staffModel.updatestaff(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Staff Member has been updated!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void clearFields() {
        TxtUpdateName.setText("");
        TxtUpdateAddress.setText("");
        TxtUpdateContact.setText("");
        TxtUpdatePosition.setText("");
        TxtUpdateSalary.setText("");
    }
}
