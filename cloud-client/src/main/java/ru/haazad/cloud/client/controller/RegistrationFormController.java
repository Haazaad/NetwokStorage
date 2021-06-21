package ru.haazad.cloud.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.NetworkService;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationFormController implements Initializable {
    private static final Logger logger = LogManager.getLogger(RegistrationFormController.class);

    private NetworkService network;

    private Stage stage;

    public TextField loginField, emailField;
    public PasswordField passwordField, confirmPasswordField;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = Factory.getNetworkService();
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
        if (!network.isConnected()) {
            network = Factory.initializeNetworkService();
        }
        network.sendCommand(new Command("register", new Object[]{
                loginField.getText(),
                Factory.getEncryptService().encryptPassword(passwordField.getText()),
                emailField.getText()
        }));
    }

    public void closeForm(ActionEvent event) {
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.close();
    }
}
