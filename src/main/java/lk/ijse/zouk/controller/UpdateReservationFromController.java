package lk.ijse.zouk.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.zouk.RegExPatterns;
import lk.ijse.zouk.dto.*;
import lk.ijse.zouk.model.GuestModel;
import lk.ijse.zouk.model.ReservationModel;
import lk.ijse.zouk.model.TableModel;

import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateReservationFromController {

    @FXML
    private AnchorPane ReservationUpdatePage;

    @FXML
    private TextField TxtUpdateGuestID;

    @FXML
    private TextField TxtUpdateReservationConatct;

    @FXML
    private DatePicker TxtUpdateReservationDate;

    @FXML
    private TextField TxtUpdateReservationNumOfguest;

    @FXML
    private TextField TxtUpdateReservationTableType;

    @FXML
    private TextField TxtUpdateReservationid;

    @FXML
    private TextField TxtUpdateReservationtableId;

    @FXML
    private TextField TxtUpdatereservationName;

    private GuestModel guestModel = new GuestModel();
    private ReservationModel reservationModel = new ReservationModel();
    private TableModel tableModel = new TableModel();

    @FXML
    void BtnReservationUpdatePageCancelOnAction(ActionEvent event) {
        ReservationUpdatePage.getScene().getWindow().hide();
    }

    @FXML
    void BtnReservationUpdatePageUpdateOnAction(ActionEvent event) {
        String guestId = TxtUpdateGuestID.getText();
        String name = TxtUpdatereservationName.getText();
        int contact = Integer.parseInt(TxtUpdateReservationConatct.getText());
        String reservationid = TxtUpdateReservationid.getText();
        int numOfguest = Integer.parseInt(TxtUpdateReservationNumOfguest.getText());
        LocalDate date = TxtUpdateReservationDate.getValue();
        String tableId = TxtUpdateReservationtableId.getText();
        String tableType = TxtUpdateReservationTableType.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidType = RegExPatterns.getValidType().matcher(tableType).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a valid GuestName").showAndWait();
            return;
        }if (!isValidType){
            new Alert(Alert.AlertType.ERROR, "Not a Valid Type").showAndWait();
        }else {

            var dto = new GuestsDto(guestId, name, contact);
            var dto1 = new ReservationDto(reservationid, date, numOfguest, guestId);
            var dto2 = new TableDto(tableId, tableType, reservationid);

            try {
                boolean isUpdated = guestModel.updateGuest(dto);
                boolean isUpdated1 = reservationModel.updateReservation(dto1);
                boolean isUpdated2 = tableModel.updateTable(dto2);

                if (isUpdated && isUpdated1 && isUpdated2) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Staff Member has been updated!").show();
                    clearFields();

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void clearFields() {
        TxtUpdateGuestID.setText("");
        TxtUpdatereservationName.setText("");
        TxtUpdateReservationConatct.setText("");
        TxtUpdateReservationid.setText("");
        TxtUpdateReservationNumOfguest.setText("");
        TxtUpdateReservationDate.setValue(null);
        TxtUpdateReservationtableId.setText("");
        TxtUpdateReservationTableType.setText("");
    }

}
