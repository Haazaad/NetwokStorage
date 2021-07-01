package ru.haazad.cloud.client.gui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.command.CommandName;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.NetworkService;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationWindowController implements Initializable, WindowController {
    private static final Logger logger = LogManager.getLogger(ApplicationWindowController.class);

    private Stage stage;

    public TextField clientPathFolder, serverPathFolder;
    public ListView<String> clientDirectoryView, serverDirectoryView;
    public Button uploadButton, downloadButton;

    private NetworkService network;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = Factory.getNetworkService();
        clientPathFolder.appendText(Factory.getView().getDirectoryPath(null).toString());
        listClientDirectory(Factory.getView().getDirectoryPath(null));
        network.sendCommand(new Command(CommandName.LS, new Object[]{Factory.getUsername()}));
    }

    private void listClientDirectory(Path path) {
        clientDirectoryView.getItems().clear();
        clientPathFolder.clear();
        clientPathFolder.appendText(path.toString());
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
        network.sendCommand(new Command(CommandName.LS, new Object[]{path}));
    }

    private String getSelectedItem(ListView<String> view) {
        String selected = view.getSelectionModel().getSelectedItem();
        logger.info(String.format("Selected item=%s", selected));
        return selected == null ? "" : selected;
    }

    public void moveToServerDirectoryByKeyPressed(KeyEvent keyEvent) {
        String path = serverPathFolder.getText() + "\\" + getSelectedItem(serverDirectoryView);
        network.sendCommand(new Command(CommandName.LS, new Object[]{path}));
    }

    public void upToClientDirectory(ActionEvent event) {
        String path = clientPathFolder.getText();
        if (!path.equals("C:\\")) {
            listClientDirectory(Paths.get(path).getParent());
        } else {
            Factory.getAlertService().showInfoAlert(String.format("Directory %s is root directory", path));
        }
    }

    public void moveToClientDirectoryByKeyPressed(KeyEvent keyEvent) {
        Path path = Paths.get(clientPathFolder.getText() + "\\" + getSelectedItem(clientDirectoryView));
        listClientDirectory(path);
    }

    public void uploadFileOnServer(ActionEvent event) {
        String srcPath = clientPathFolder.getText() + "\\" + getSelectedItem(clientDirectoryView);
        String dstPath = serverPathFolder.getText();
        Factory.getFileTransferService().sendFile(srcPath, dstPath);
    }

    public void upToServerDirectory(ActionEvent event) {
        String[] curPath = serverPathFolder.getText().replace("\\", " ").split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < curPath.length - 1; i++) {
            sb.append(curPath[i]).append("\\");
        }
        network.sendCommand(new Command(CommandName.LS, new Object[]{sb.toString()}));
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void close() {
        stage.close();
        network.closeConnection();
    }

    @Override
    public void showErrorAlert(String cause) {
        Platform.runLater(() -> Factory.getAlertService().showErrorAlert(cause));
    }

    public void showInfoAlert(String cause) {
        Platform.runLater(() -> Factory.getAlertService().showInfoAlert(cause));
    }

    @Override
    public void processAction(Object[] args) {
        String path = (String) args[0];
        List<String> listFiles = (List<String>) args[1];
        Platform.runLater(() -> {
                    serverPathFolder.clear();
                    serverPathFolder.appendText(path);
                    serverDirectoryView.getItems().clear();
                    for (String s : listFiles) {
                        serverDirectoryView.getItems().add(s);
                    }
                }
        );

    }
}
