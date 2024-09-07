package lk.ijse.zouk.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFromController {

    @FXML
    private JFXButton BtnEvent;


    @FXML
    private JFXButton BtnHomePage;

    @FXML
    private JFXButton BtnInventory;

    @FXML
    private JFXButton BtnReservation;

    @FXML
    private JFXButton BtnStaff;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private AnchorPane rootNode;

    public void initialize() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/HomePage_form.fxml"));
        rootHome.getChildren().clear();
        rootHome.getChildren().add(anchorPane);
    }

    @FXML
    void BtnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/HomePage_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(anchorPane);
    }

    @FXML
    void BtnEventOnAction(ActionEvent event) throws IOException {
        System.out.println("Success");
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Event_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(anchorPane);
    }

    @FXML
    void BtnInventoryOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Enventory_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(anchorPane);
    }

    @FXML
    void BtnLogOutOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Login_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void BtnReservationOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/reservation_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(anchorPane);
    }

    @FXML
    void BtnStaffOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/staff_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(anchorPane);
    }
}
