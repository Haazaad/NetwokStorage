package ru.haazad.cloud.client.factory;

import javafx.fxml.Initializable;
import ru.haazad.cloud.client.ClientApp;
import ru.haazad.cloud.client.service.AlertService;
import ru.haazad.cloud.client.service.CommandService;
import ru.haazad.cloud.client.service.EncryptPasswordService;
import ru.haazad.cloud.client.service.NetworkService;
import ru.haazad.cloud.client.service.impl.ClientAlertService;
import ru.haazad.cloud.client.service.impl.ClientCommandDictionaryService;
import ru.haazad.cloud.client.service.impl.MD5EncryptPasswordService;
import ru.haazad.cloud.client.service.impl.NettyNetworkService;
import ru.haazad.cloud.client.service.impl.command.*;
import ru.haazad.cloud.client.service.CommandDictionaryService;

import java.util.Arrays;
import java.util.List;

public class Factory {

    public static String getUsername() {
        return ClientApp.getUsername();
    }

    public static void setUsername(String username) {
        ClientApp.setUsername(username);
    }

    public static Initializable getActiveController() {
        return ClientApp.getActiveController();
    }

    public static void setActiveController(Initializable controller) {
        ClientApp.setActiveController(controller);
    }

    public static Initializable getSecondaryController() { return ClientApp.getSecondaryController();}

    public static void setSecondaryController(Initializable controller) {ClientApp.setSecondaryController(controller);}

    public static NetworkService initializeNetworkService() {
        return NettyNetworkService.initializeNetwork();
    }

    public static NetworkService getNetworkService() {
        return NettyNetworkService.getNetwork();
    }

    public static EncryptPasswordService getEncryptService() {
        return MD5EncryptPasswordService.getEncryptService();
    }

    public static AlertService getAlertService() {
        return ClientAlertService.getAlertService();
    }

    public static CommandDictionaryService getCommandDictionary() {
        return new ClientCommandDictionaryService();
    }

    public static ViewFilesInClientDirectory getView() {
        return new ViewFilesInClientDirectory().getView();
    }

    public static List<CommandService> getCommandServices() {
        return Arrays.asList(new SuccessLogin(),
                new BadLogin(),
                new SuccessRegistration(),
                new ErrorRegistration(),
                new SuccessViewFilesOnServer());
    }
}
