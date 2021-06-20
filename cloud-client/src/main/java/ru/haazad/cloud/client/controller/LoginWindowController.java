package ru.haazad.cloud.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.NetworkService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {
    private static final Logger logger = LogManager.getLogger(LoginWindowController.class);

    private Stage stage;

    protected NetworkService networkService;

    public TextField loginField;
    public PasswordField passwordField;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void login(ActionEvent event) {
        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            Factory.getAlertService().showErrorAlert("Login/password cannot be empty");
            return;
        }
        if (!networkService.isConnected()) {
            networkService = Factory.initializeNetworkService();
        }
        networkService.sendCommand(new Command("login", new Object[]{
                loginField.getText(),
                Factory.getEncryptService().encryptPassword(passwordField.getText())
        }));
        loginField.clear();
        passwordField.clear();
    }

    public void disconnect() {
        networkService.closeConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = Factory.initializeNetworkService();
    }


    public void callRegistrationForm(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/registrationWindow.fxml"));
            Parent child = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cloud Client. Register new user");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(new Scene(child));
            stage.setOnCloseRequest((event) -> stage.close());
            stage.show();
        } catch (IOException e) {
            logger.throwing(Level.ERROR, e);
        }
    }
}
