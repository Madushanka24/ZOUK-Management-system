package lk.ijse.zouk.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.zouk.RegExPatterns;
import lk.ijse.zouk.dto.EventDto;
import lk.ijse.zouk.dto.InventoryDto;
import lk.ijse.zouk.dto.SupplierDto;
import lk.ijse.zouk.dto.TicketDto;
import lk.ijse.zouk.model.EventModel;
import lk.ijse.zouk.model.TicketModel;

import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateEventFromController {
    @FXML
    private DatePicker TxtEventDate;

    @FXML
    private TextField TxtUpdateEventGuestArtist;

    @FXML
    private TextField TxtUpdateEventID;

    @FXML
    private TextField TxtUpdateEventName;

    @FXML
    private TextField TxtUpdateEventPlatinumPrice;

    @FXML
    private TextField TxtUpdateEventVipPrice;

    @FXML
    private AnchorPane UpdateEventPage;

    private EventModel eventModel = new EventModel();

    private TicketModel ticketModel = new TicketModel();

    @FXML
    void BtnEventUpdatePageCancelOnAction(ActionEvent event) {
        UpdateEventPage.getScene().getWindow().hide();
    }

    @FXML
    void BtnEventUpdatePageUpdateOnAction(ActionEvent event) {
        String id = TxtUpdateEventID.getText();
        String name = TxtUpdateEventName.getText();
        LocalDate date = TxtEventDate.getValue();
        double vipPrice = Double.parseDouble(TxtUpdateEventVipPrice.getText());
        double platinumPrice = Double.parseDouble(TxtUpdateEventPlatinumPrice.getText());
        String guestArtist = TxtUpdateEventGuestArtist.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a valid EventName").showAndWait();
        }else {

            var dto = new EventDto(id, name, date, guestArtist);
            var dto1 = new TicketDto(vipPrice, platinumPrice, id);

            try {
                boolean isUpdated = eventModel.updateGuest(dto);
                boolean isUpdated1 = ticketModel.updateTicket(dto1);
                if (isUpdated && isUpdated1) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Event has been updated!").show();
                    clearFields();

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void clearFields() {
        TxtUpdateEventID.setText("");
        TxtUpdateEventName.setText("");
        TxtUpdateEventGuestArtist.setText("");
        TxtEventDate.setValue(null);
        TxtUpdateEventVipPrice.setText("");
        TxtUpdateEventPlatinumPrice.setText("");
    }
}
