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
import lk.ijse.zouk.dto.*;
import lk.ijse.zouk.model.GuestModel;
import lk.ijse.zouk.model.ReservationModel;
import lk.ijse.zouk.model.TableModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ReservationFromController {

    @FXML
    private JFXButton BtnReservationSave;

    @FXML
    private JFXButton BtnReservationUpdate;

    @FXML
    private TableColumn<?, ?> DateColumn;

    @FXML
    private Label GuestIDLabel;

    @FXML
    private TableView<GuestsDto> GuestTable;

    @FXML
    private TableColumn<?, ?> NumOfGueColumn;

    @FXML
    private AnchorPane PageReservation;

    @FXML
    private Label ReservationIDLabel;

    @FXML
    private TableColumn<?, ?> ReservationIdColumn;

    @FXML
    private TableView<ReservationDto> ReservationTablle;

    @FXML
    private TableColumn<?, ?> TableColumnContact;

    @FXML
    private Label TableIDLabel;

    @FXML
    private TableColumn<?, ?> TableIdColumn;

    @FXML
    private TableColumn<?, ?> TableTypeColumn;

    @FXML
    private TableView<TableDto> Tabletable;

    @FXML
    private TextField TxtContact;

    @FXML
    private TextField TxtGuestName;

    @FXML
    private TextField TxtNumberOfGuests;

    @FXML
    private TextField TxtReservationSearch;

    @FXML
    private DatePicker TxtReservationdate;

    @FXML
    private TextField TxtTableType;

    @FXML
    private TableColumn<?, ?> tableColumnGuestID;

    @FXML
    private TableColumn<?, ?> tableColumnGuestname;


    private GuestModel guestModel = new GuestModel();
    private ReservationModel reservationModel = new ReservationModel();

    private TableModel tableModel = new TableModel();

    public void initialize() {
        setCellValueFactory();
        generateNextGuestID();
        generateNextTableID();
        generateNextReservationID();
        loadAllGuests();
        loadAllReservation();
        loadAllTable();
    }

    private void setCellValueFactory() {
        tableColumnGuestID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnGuestname.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        ReservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NumOfGueColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfGuests"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    private void  generateNextGuestID(){
        try {
            String previousGuestID = GuestIDLabel.getText();
            String guestID = guestModel.generateNextGuestID();
            GuestIDLabel.setText(guestID);
            clearFields();
            if (btnClearPressed){
                GuestIDLabel.setText(previousGuestID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void  generateNextReservationID(){
        try {
            String previousReservationID = ReservationIDLabel.getText();
            String reservationID = reservationModel.generateNextReservationID();
            ReservationIDLabel.setText(reservationID);
            clearFields();
            if (btnClearPressed){
                ReservationIDLabel.setText(previousReservationID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void  generateNextTableID(){
        try {
            String previousTableID = TableIDLabel.getText();
            String tableID = tableModel.generateNextTableID();
            TableIDLabel.setText(tableID);
            clearFields();
            if (btnClearPressed){
                TableIDLabel.setText(previousTableID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private boolean btnClearPressed = false;
    private void clearFields() {
        TxtGuestName.setText("");
        TxtContact.setText("");
        TxtNumberOfGuests.setText("");
        TxtReservationdate.setValue(null);
        TxtTableType.setText("");
        TxtReservationSearch.setText("");
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextGuestID();
        generateNextReservationID();
        generateNextTableID();
    }

    private void loadAllGuests() {
        var guestModel1 = new GuestModel();

        ObservableList<GuestsDto> obList = FXCollections.observableArrayList();

        try {
            List<GuestsDto> guestsDtos = guestModel1.getAllGuest();

            for (GuestsDto dto : guestsDtos) {
                obList.add(
                        new GuestsDto(dto.getId(), dto.getName(), dto.getContact())
                );
            }
            GuestTable.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllReservation() {
        var reservationModel1 = new ReservationModel();

        ObservableList<ReservationDto> obList1 = FXCollections.observableArrayList();

        try {
            List<ReservationDto> reservationDtos = reservationModel1.getAllReservations();

            for (ReservationDto dto : reservationDtos) {
                obList1.add(
                        new ReservationDto(
                                dto.getId(),
                                dto.getDate(),
                                dto.getNumberOfGuests(),
                                dto.getGuestID()
                        )
                );
            }
            ReservationTablle.setItems(obList1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllTable() {
        var tableModel1 = new TableModel();

        ObservableList<TableDto> obList2 = FXCollections.observableArrayList();

        try {
            List<TableDto> tableDtos = tableModel1.getAllTables();

            for (TableDto dto : tableDtos) {
                obList2.add(
                        new TableDto(
                                dto.getId(),
                                dto.getType(),
                                dto.getReservationId()
                        )
                );
            }
            Tabletable.setItems(obList2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void BtnReservationDelete(ActionEvent event) {
        String id = TxtReservationSearch.getText();

        try {
            boolean isDeleted1 = guestModel.deleteGuest(id);
            boolean isDeleted2 = reservationModel.deleteReservation(id);
            boolean isDeleted3 = tableModel.deleteTable(id);
            if(isDeleted1) {
                new Alert(Alert.AlertType.CONFIRMATION, "Reservation has been deleted!").show();
                clearFields();
                generateNextReservationID();
                generateNextGuestID();
                generateNextTableID();
                loadAllTable();
                loadAllReservation();
                loadAllGuests();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Reservation Id not matched!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void BtnReservationSave(ActionEvent event) {
        String guestId = GuestIDLabel.getText();
        String name = TxtGuestName.getText();
        int contact = Integer.parseInt((TxtContact.getText()));
        String reservationId = ReservationIDLabel.getText();
        int numberOfGuests = Integer.parseInt(TxtNumberOfGuests.getText());
        LocalDate date = TxtReservationdate.getValue();
        String tableId = TableIDLabel.getText();
        String type = TxtTableType.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidType = RegExPatterns.getValidType().matcher(type).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a valid GuestName").showAndWait();
            return;
        }if (!isValidType){
            new Alert(Alert.AlertType.ERROR, "Not a Valid Type").showAndWait();
        }else {

            var guestsDto = new GuestsDto(guestId, name, contact);
            var reservationDto = new ReservationDto(reservationId, date, numberOfGuests, guestId);
            var tableDto = new TableDto(tableId, type, reservationId);
            try {
                boolean isSaved1 = guestModel.saveGuest(guestsDto);
                boolean isSaved2 = reservationModel.saveReservation(reservationDto);
                boolean isSaved3 = tableModel.saveTable(tableDto);

                if (isSaved1 && isSaved2 && isSaved3) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Inventory has been saved!").show();
                    clearFields();
                    generateNextGuestID();
                    generateNextReservationID();
                    generateNextTableID();
                    loadAllGuests();
                    loadAllReservation();
                    loadAllTable();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void BtnReservationUpdate(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/updateReservation_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void txtGoToContact(ActionEvent event) {
        TxtContact.requestFocus();
    }

    @FXML
    void txtGoToDate(ActionEvent event) {
        TxtReservationdate.requestFocus();
    }

    @FXML
    void txtGoToNumOfGuest(ActionEvent event) {
        TxtNumberOfGuests.requestFocus();
    }

    @FXML
    void txtGoToSave(ActionEvent event) {
        BtnReservationSave(new ActionEvent());
    }

    @FXML
    void txtGoToType(ActionEvent event) {
        TxtTableType.requestFocus();
    }
}
