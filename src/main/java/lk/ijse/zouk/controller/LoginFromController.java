package lk.ijse.zouk.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.zouk.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFromController implements Initializable {
    @FXML
    private AnchorPane PageLogin;

    @FXML
    private TextField txtPass;

    @FXML
    private TextField txtUserName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private UserModel userModel = new UserModel();

    private void login() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) this.PageLogin.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void BtnLogOnAction(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String pw = txtPass.getText();

            try {
                boolean isValid = userModel.isValidUser(userName, pw);
                if (isValid) {
                    login();
                } else {
                    new Alert(Alert.AlertType.ERROR, "User Name And Password Did Not Matched try again").showAndWait();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }
    @FXML
    void txtGoToLogin(ActionEvent event) throws IOException {
        BtnLogOnAction(new ActionEvent());
    }

    @FXML
    void txtGoToPassword(ActionEvent event) {
        txtPass.requestFocus();
    }

    @FXML
    void BtnRegOnAction(ActionEvent event) throws Exception{
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/register_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) this.PageLogin.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Register Page");
        stage.centerOnScreen();
    }
}
