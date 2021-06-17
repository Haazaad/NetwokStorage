package ru.haazad.cloud.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.NetworkService;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private static final Logger logger = LogManager.getLogger(MainWindowController.class);

    private NetworkService networkService;
    private boolean isLoginOk;

    public AnchorPane loginWindow, mainWindowForm;
    public TextField loginField;
    public PasswordField passwordField;

    public void checkLogin(boolean isLogin) {
        isLoginOk = isLogin;
        loginWindow.setVisible(!isLoginOk);
        loginWindow.setManaged(!isLoginOk);
        mainWindowForm.setVisible(isLoginOk);
        mainWindowForm.setVisible(isLoginOk);
    }

    public void login(ActionEvent event) {
        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlertMessage("Login/password cannot be empty");
            return;
        }
        if (!networkService.isConnected()) {
            networkService = Factory.getNetworkService();
        }
        sendCommand("login " + loginField.getText() + " " + Factory.getEncryptService().encryptPassword(passwordField.getText()));
        loginField.clear();
        passwordField.clear();
    }

    public void disconnect() {
        networkService.closeConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkLogin(false);
        networkService = Factory.getNetworkService();
    }

    private void showAlertMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cloud Client");
        alert.setContentText(msg);
        alert.setHeaderText(null);
        alert.showAndWait();
        logger.error(msg);
    }

    private void sendCommand(String command) {
        String[] textCommand = command.trim().split("\\s");
        if (textCommand.length > 1) {
            String[] commandArgs = Arrays.copyOfRange(textCommand, 1, textCommand.length);
            networkService.sendCommand(new Command(textCommand[0], commandArgs));
        }
    }

    public void registerNewUser(MouseDragEvent mouseDragEvent) {
    }
}
