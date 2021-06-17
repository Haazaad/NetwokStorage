package ru.haazad.cloud.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.client.factory.Factory;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationFormController extends MainWindowController implements Initializable {
    private static final Logger logger = LogManager.getLogger(RegistrationFormController.class);
    
    public TextField loginField, emailField;
    public PasswordField passwordField, confirmPasswordField;

    private boolean isRegister;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isRegister = false;
    }

    public void register(ActionEvent event) {
        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty() || emailField.getText().isEmpty()) {
            Factory.getAlertService().showInfoAlert("Not all required fields have been filled in");
            return;
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            Factory.getAlertService().showInfoAlert("The password was incorrectly verified");
            return;
        }
        if (!networkService.isConnected()) {
            networkService = Factory.getNetworkService();
        }
        String sb = "register " +
                Factory.getEncryptService().encryptPassword(passwordField.getText()) +
                " " +
                emailField.getText();
        sendCommand(sb);
    }

    public void closeForm(ActionEvent event) {
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.close();
    }
}
