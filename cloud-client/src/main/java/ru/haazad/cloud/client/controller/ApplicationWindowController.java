package ru.haazad.cloud.client.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.NetworkService;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationWindowController implements Initializable {
    public TextField clientPathFolder, serverPathFolder;
    public TableView clientFolder, serverFolder;
    public Button uploadButton, downloadButton;

    private NetworkService network;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = Factory.getNetworkService();
    }

    public void disconnect() {
        network.closeConnection();
    }
}
