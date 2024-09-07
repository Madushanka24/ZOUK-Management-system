package lk.ijse.zouk.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.zouk.dto.HomePageDto;
import lk.ijse.zouk.dto.StaffDto;
import lk.ijse.zouk.model.HomePageModel;
import lk.ijse.zouk.model.StaffModel;

import java.sql.SQLException;
import java.util.List;

public class UpdateHomePageFromController {
    @FXML
    private AnchorPane PageHomePageUpdate;

    @FXML
    private TextField TxtSoldTickets;

    @FXML
    private TextField TxtTotalEvent;

    @FXML
    private TextField TxtTotalProfit;

    @FXML
    private TextField TxtID;

    private HomePageModel homePageModel = new HomePageModel();

    @FXML
    void BtnHomePageUpdatePageCancelOnAction(ActionEvent event) {
        PageHomePageUpdate.getScene().getWindow().hide();
    }

    @FXML
    void BtnHomePageUpdatePageUpdateOnAction(ActionEvent event) {
        String id = TxtID.getText();
        int tickets = Integer.parseInt((TxtSoldTickets.getText()));
        int events = Integer.parseInt(TxtTotalEvent.getText());
        double profit = Double.parseDouble(TxtTotalProfit.getText());

        var dto = new HomePageDto(id,profit,tickets,events);


        try {
            boolean isUpdated = homePageModel.updateSummary(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Summary has been updated!").show();
                clearFields();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void clearFields() {
        TxtTotalProfit.setText("");
        TxtTotalEvent.setText("");
        TxtSoldTickets.setText("");
    }

}
