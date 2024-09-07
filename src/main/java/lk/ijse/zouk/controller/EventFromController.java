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
import lk.ijse.zouk.dto.EventDto;
import lk.ijse.zouk.dto.StaffDto;
import lk.ijse.zouk.dto.TicketDto;
import lk.ijse.zouk.model.EventModel;
import lk.ijse.zouk.model.TicketModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EventFromController {

    @FXML
    private TableColumn<?, ?> ColumnEventDate;

    @FXML
    private TableColumn<?, ?> ColumnEventId;

    @FXML
    private TableColumn<?, ?> ColumnEventName;

    @FXML
    private TableColumn<?, ?> ColumnGuestArtist;

    @FXML
    private TableColumn<?, ?> ColumnPlatinymPrice;

    @FXML
    private TableColumn<?, ?> ColumnVipPrice;

    @FXML
    private Label EventIDLabel;

    @FXML
    private TableView<EventDto> EventTable;

    @FXML
    private TableView<TicketDto> TicketTable;

    @FXML
    private DatePicker TxtEventDate;

    @FXML
    private TextField TxtEventName;

    @FXML
    private TextField TxtEventSearch;

    @FXML
    private TextField TxtGuestArtist;

    @FXML
    private TextField TxtPlatinumPrice;

    @FXML
    private TextField TxtVipPrice;

    private EventModel eventModel = new EventModel();

    private TicketModel ticketModel = new TicketModel();


    public void initialize() {
        setCellValueFactory();
        loadAllEvent();
        loadAllTickets();
        generateNextEventID();
    }

    private void  generateNextEventID(){
        try {
            String previousEventID = EventIDLabel.getText();
            String eventID = eventModel.generateNextEventID();
            EventIDLabel.setText(eventID);
            clearFields();
            if (btnClearPressed){
                EventIDLabel.setText(previousEventID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private boolean btnClearPressed = false;

    private void clearFields() {
        TxtEventName.setText("");
        TxtEventDate.setValue(null);
        TxtVipPrice.setText("");
        TxtPlatinumPrice.setText("");
        TxtGuestArtist.setText("");
    }

    private void setCellValueFactory() {
        ColumnEventId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColumnEventDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        ColumnGuestArtist.setCellValueFactory(new PropertyValueFactory<>("guestArtist"));
        ColumnVipPrice.setCellValueFactory(new PropertyValueFactory<>("vipTicketPrice"));
        ColumnPlatinymPrice.setCellValueFactory(new PropertyValueFactory<>("platinumTicketPrice"));

    }

    private void loadAllEvent() {
        var eventModel1 = new EventModel();

        ObservableList<EventDto> obList = FXCollections.observableArrayList();

        try {
            List<EventDto> eventDtos = eventModel1.getAllEvent();

            for (EventDto dto : eventDtos) {
                obList.add(
                        new EventDto(
                                dto.getId(),
                                dto.getName(),
                                dto.getDate(),
                                dto.getGuestArtist()
                        )
                );
            }

            EventTable.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllTickets() {
        var ticketModel1 = new TicketModel();

        ObservableList<TicketDto> obList1 = FXCollections.observableArrayList();

        try {
            List<TicketDto> ticketDtos = ticketModel1.getAllTickes();

            for (TicketDto dto : ticketDtos) {
                obList1.add(
                        new TicketDto(
                                dto.getVipTicketPrice(),
                                dto.getPlatinumTicketPrice(),
                                dto.getEventID()
                        )
                );
            }

            TicketTable.setItems(obList1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void BtnEventDeleteOnAction(ActionEvent event) {
        String id = TxtEventSearch.getText();

        try {
            boolean isDeleted = eventModel.deleteEvent(id);
            boolean isDeleted1 = ticketModel.deleteTickets(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Event  has been deleted!").show();
                loadAllTickets();
                loadAllEvent();
                clearFields();
                generateNextEventID();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Event Id not matched!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void BtnEventSaveOnAction(ActionEvent event) {
        String id =EventIDLabel.getText();
        String name = TxtEventName.getText();
        LocalDate date = TxtEventDate.getValue();
        String guestArtist = TxtGuestArtist.getText();
        double vipPrice = Double.parseDouble(TxtVipPrice.getText());
        double platinumPrice = Double.parseDouble(TxtPlatinumPrice.getText());

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a valid EventName").showAndWait();
        }else {

            var eventDto = new EventDto(id, name, date, guestArtist);
            var ticketDto = new TicketDto(vipPrice, platinumPrice, id);

            try {
                boolean isSaved = eventModel.saveEvent(eventDto);
                boolean isSaved1 = ticketModel.saveTicket(ticketDto);

                if (isSaved && isSaved1) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Event has been saved!").show();
                    clearFields();
                    generateNextEventID();
                    loadAllEvent();
                    loadAllTickets();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void BtnEventUpdateOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/updateEvent_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void TxtGoTOPlatinumPrice(ActionEvent event) {
        TxtPlatinumPrice.requestFocus();
    }

    @FXML
    void TxtGoToDate(ActionEvent event) {
        TxtEventDate.getValue();
    }

    @FXML
    void TxtGoToGuestArtist(ActionEvent event) {
        TxtGuestArtist.requestFocus();
    }

    @FXML
    void TxtGoToSave(ActionEvent event) {
        BtnEventSaveOnAction(new ActionEvent());
    }

    @FXML
    void TxtGoToVipPrice(ActionEvent event) {
        TxtVipPrice.requestFocus();
    }

}
