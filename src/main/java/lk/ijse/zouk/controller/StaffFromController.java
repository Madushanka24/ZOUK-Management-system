package lk.ijse.zouk.controller;

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
import lk.ijse.zouk.dto.StaffDto;
import lk.ijse.zouk.model.StaffModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StaffFromController {


    @FXML
    private AnchorPane StaffPage;

    @FXML
    private TableView<StaffDto> StaffTable;

    @FXML
    private TableColumn<?, ?> TableStaffAddress;

    @FXML
    private TableColumn<?, ?> TableStaffContact;

    @FXML
    private TableColumn<?, ?> TableStaffId;

    @FXML
    private TableColumn<?, ?> TableStaffName;

    @FXML
    private TableColumn<?, ?> TableStaffPosition;

    @FXML
    private TableColumn<?, ?> TableStaffSalary;

    @FXML
    private TextField TxtAddress;

    @FXML
    private TextField TxtContact;

    @FXML
    private TextField TxtPosition;

    @FXML
    private TextField TxtSalary;

    @FXML
    private TextField TxtStaffName;

    @FXML
    private TextField TxtStaffSearch;
    @FXML
    private Label StaffIDLabel;
    
    private StaffModel staffModel = new StaffModel();

    public void initialize() {
        setCellValueFactory();
        loadAllStaff();
        generateNextsStaffID();
    }

    private void  generateNextsStaffID(){
        try {
            String previousStaffID = StaffIDLabel.getText();
            String staffID = staffModel.generateNextStaff();
            StaffIDLabel.setText(staffID);
            clearFields();
            if (btnClearPressed){
                StaffIDLabel.setText(previousStaffID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private boolean btnClearPressed = false;

    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextsStaffID();
    }

    private void setCellValueFactory() {
        TableStaffId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableStaffName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableStaffAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableStaffContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        TableStaffPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        TableStaffSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        String id = TxtStaffSearch.getText();

        try {
            boolean isDeleted = staffModel.deleteStaff(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Staff member has been deleted!").show();
                loadAllStaff();
                clearFields();
                generateNextsStaffID();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Staff member Id not matched!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void BtnStaffSaveOnAction(ActionEvent event) {
        String id =StaffIDLabel.getText();
        String name = TxtStaffName.getText();
        String address = TxtAddress.getText();
        int contact = Integer.parseInt(TxtContact.getText());
        String position = TxtPosition.getText();
        double salary = Double.parseDouble(TxtSalary.getText());

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidAddress = RegExPatterns.getValidAddress().matcher(address).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a valid Name").showAndWait();
            return;
        }if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Address").showAndWait();
        }else {

            var staffDto = new StaffDto(id, name, address, contact, position, salary);

            try {
                boolean isSaved = staffModel.saveStaff(staffDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Staff member has been saved!").show();
                    clearFields();
                    generateNextsStaffID();
                    loadAllStaff();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    private void clearFields() {
        TxtStaffName.setText("");
        TxtAddress.setText("");
        TxtContact.setText("");
        TxtPosition.setText("");
        TxtSalary.setText("");
        TxtStaffSearch.setText("");
    }

    private void loadAllStaff() {
        var staffModel1 = new StaffModel();

        ObservableList<StaffDto> obList = FXCollections.observableArrayList();

        try {
            List<StaffDto> staffDtos = staffModel1.getAllStaff();

            for (StaffDto dto : staffDtos) {
                obList.add(
                        new StaffDto(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getContact(),
                                dto.getPosition(),
                                dto.getSalary()
                        )
                );
            }

            StaffTable.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BtnStaffUpdateOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/updateStaff_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void txtGoTOContact(ActionEvent event) {
        TxtContact.requestFocus();
    }

    @FXML
    void txtGoToAddress(ActionEvent event) {
        TxtAddress.requestFocus();
    }

    @FXML
    void txtGoToPosition(ActionEvent event) {
        TxtPosition.requestFocus();
    }

    @FXML
    void txtGoToSalary(ActionEvent event) {
        TxtSalary.requestFocus();
    }

    @FXML
    void txtGoToSave(ActionEvent event) {
        BtnStaffSaveOnAction(new ActionEvent());
    }

}
