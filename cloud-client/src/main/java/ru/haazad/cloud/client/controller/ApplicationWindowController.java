package ru.haazad.cloud.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.NetworkService;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationWindowController implements Initializable {

    public TextField clientPathFolder, serverPathFolder;
    public ListView<String> clientDirectoryView, serverDirectoryView;
    public Button uploadButton, downloadButton;

    private NetworkService network;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = Factory.getNetworkService();
        clientPathFolder.appendText(Factory.getView().getDirectoryPath(null).toString());
        listClientDirectory(Factory.getView().getDirectoryPath(null));
    }

    public void disconnect() {
        network.closeConnection();
    }

    private void listClientDirectory(Path path) {
        List<String> filesInDirectory = Factory.getView().getFilesInDirectory(path);
        for (String s : filesInDirectory) {
            clientDirectoryView.getItems().add(s);
        }
    }

    public void moveToClientDirectory(ActionEvent event) {
        clientDirectoryView.getItems().clear();
        listClientDirectory(Factory.getView().getDirectoryPath(clientPathFolder.getText()));
    }

    public void moveToServerDirectory(ActionEvent event) {
        String path = serverPathFolder.getText();
        network.sendCommand(new Command("ls", new Object[]{Factory.getUsername()}));
    }
}
