package ru.haazad.cloud.client.service.impl.command;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.controller.ApplicationWindowController;
import ru.haazad.cloud.service.CommandService;

import java.io.IOException;

public class SuccessLogin implements CommandService {
    private static final Logger logger = LogManager.getLogger(SuccessLogin.class);

    @Override
    public Command processCommand(Command command) {
        try {
            FXMLLoader secondary = new FXMLLoader(getClass().getClassLoader().getResource("view/applicationWindow.fxml"));
            Parent child = secondary.load();
            Platform.runLater(() -> {
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Cloud Client.");
                stage.setResizable(false);
                stage.setScene(new Scene(child));
                ApplicationWindowController secondaryController = secondary.getController();
                secondaryController.setUsername((String) command.getArgs()[0]);
                stage.setOnCloseRequest((event) -> secondaryController.disconnect());
                stage.show();
            });
        } catch (IOException e) {
            logger.throwing(Level.ERROR, e);
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "login_ok";
    }
}
