package ru.haazad.cloud.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.NetworkService;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private NetworkService networkService;

    public TextField loginField;
    public PasswordField passwordField;

    public void login(ActionEvent event) {
        networkService.sendCommand(loginField.getText());
        loginField.clear();
    }

    public void shutdown() {
        networkService.closeConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = Factory.getNetworkService();
    }
}
