package ru.haazad.cloud.client.service.impl.command;

import javafx.application.Platform;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.controller.ApplicationWindowController;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.CommandService;

import java.util.List;

public class SuccessViewFilesOnServer implements CommandService {
    @Override
    public void processCommand(Command command) {
        ApplicationWindowController controller = (ApplicationWindowController) Factory.getActiveController();
        String path = (String) command.getArgs()[0];
        List<String> listFiles = (List<String>) command.getArgs()[1];
        Platform.runLater(() -> {
                    controller.serverPathFolder.clear();
                    controller.serverPathFolder.appendText(path);
                    controller.serverDirectoryView.getItems().clear();
                    for (String s : listFiles) {
                        controller.serverDirectoryView.getItems().add(s);
                    }
                }
        );

    }

    @Override
    public String getCommand() {
        return "ls";
    }
}
