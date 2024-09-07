package lk.ijse.zouk.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.zouk.db.DbConnection;
import lk.ijse.zouk.dto.HomePageDto;
import lk.ijse.zouk.model.HomePageModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HomePage implements Initializable {

    static int tickets;
    static int events;
    static int profit;
    @FXML
    private Label LblSoldTicketId;

    @FXML
    private Label LblTotalProfit;

    @FXML
    private Label LblTotalevents;

    @FXML
    private AnchorPane PageDashboard;

    private HomePageModel homePageModel = new HomePageModel();



    @FXML
    void BtnHomePageUpdateOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/updateHomepage_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    private void SetDataToTotalEvents(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<HomePageDto> idList = homePageModel.getAllSummary();
            for (HomePageDto dto : idList) {
                obList.add(String.valueOf(dto.getTotalEvents()));
            }
            LblTotalevents.setText(String.valueOf(obList));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private void SetDataToTotalProfits(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<HomePageDto> idList = homePageModel.getAllSummary();
            for (HomePageDto dto : idList) {
                obList.add(String.valueOf(dto.getTotalProfit()));
            }
            LblTotalProfit.setText(String.valueOf(obList));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void SetDataToTotalTickets(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<HomePageDto> idList = homePageModel.getAllSummary();
            for (HomePageDto dto : idList) {
                obList.add(String.valueOf(dto.getSoldTickets()));
            }
            LblSoldTicketId.setText(String.valueOf(obList));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void BtnHomePageReportOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream =getClass().getResourceAsStream("/Reports/monthSummary2.jrxml");
        JasperDesign load =JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport =JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport,null,
                DbConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint,false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SetDataToTotalEvents();
        SetDataToTotalProfits();
        SetDataToTotalTickets();
    }
}
