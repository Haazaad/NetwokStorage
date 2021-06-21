package ru.haazad.cloud.client.service.impl.command;

import javafx.application.Platform;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.controller.RegistrationFormController;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.CommandService;

public class SuccessRegistration implements CommandService {
    @Override
    public void processCommand(Command command) {
        RegistrationFormController controller = (RegistrationFormController) Factory.getSecondaryController();
        Platform.runLater(() -> {
            controller.getStage().close();
            Factory.getAlertService().showInfoAlert("Registration was successful. Please log in.");
        });
    }

    @Override
    public String getCommand() {
        return "register_ok";
    }
}
