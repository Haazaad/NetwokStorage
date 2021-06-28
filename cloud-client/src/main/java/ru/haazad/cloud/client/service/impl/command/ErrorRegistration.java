package ru.haazad.cloud.client.service.impl.command;

import javafx.application.Platform;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.CommandService;

public class ErrorRegistration implements CommandService {
    @Override
    public void processCommand(Command command) {
        Platform.runLater(() ->
                Factory.getAlertService().showErrorAlert(command.getArgs()[0]));
    }

    @Override
    public String getCommand() {
        return "register_bad";
    }
}
