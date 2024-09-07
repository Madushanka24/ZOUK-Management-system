package lk.ijse.zouk.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.zouk.RegExPatterns;
import lk.ijse.zouk.dto.RegistrationDto;
import lk.ijse.zouk.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterFromController {


    @FXML
    private AnchorPane PageRegister;

    @FXML
    private TextField TxtRegPass;

    @FXML
    private TextField TxtRegUserName;


    private UserModel userModel = new UserModel();

    @FXML
    void BtnBackOnAction(MouseEvent event) throws IOException{
        PageRegister.getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/Login_form.fxml"))));
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void BtnRegOnAction(ActionEvent event) {
        String user = TxtRegUserName.getText();
        String pw = TxtRegPass.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(user).matches();
        boolean isValidPass = RegExPatterns.getValidPassword().matcher(pw).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a valid UserName").showAndWait();
            return;
        }if (!isValidPass){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Password").showAndWait();
        }else {

            if (user.isEmpty() || pw.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Can Not Leave Password or User Name Empty!").showAndWait();
                return;
            }

            var dto = new RegistrationDto(user, pw);
            try {
                boolean checkDuplicates = userModel.check(user, pw);
                if (checkDuplicates) {
                    new Alert(Alert.AlertType.ERROR, "Duplicate Entry").showAndWait();
                    return;
                }

                boolean isRegistered = userModel.registerUser(dto);
                if (isRegistered) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Your Account Has been Created").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void txtGoToPassword(ActionEvent event) {
        TxtRegPass.requestFocus();
    }

    @FXML
    void txtGoToRegister(ActionEvent event) {
        BtnRegOnAction(new ActionEvent());
    }
}
