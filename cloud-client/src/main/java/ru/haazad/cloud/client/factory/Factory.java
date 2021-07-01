package ru.haazad.cloud.client.factory;

import ru.haazad.cloud.client.ClientApp;
import ru.haazad.cloud.client.gui.controller.WindowController;
import ru.haazad.cloud.client.gui.service.AlertService;
import ru.haazad.cloud.client.service.*;
import ru.haazad.cloud.client.gui.service.impl.ClientAlertService;
import ru.haazad.cloud.client.service.impl.ClientCommandDictionaryService;
import ru.haazad.cloud.client.service.impl.MD5EncryptPasswordService;
import ru.haazad.cloud.client.service.impl.NettyNetworkService;
import ru.haazad.cloud.client.service.impl.command.*;

import java.util.Arrays;
import java.util.List;

public class Factory {

    public static String getUsername() {
        return ClientApp.getUsername();
    }

    public static void setUsername(String username) {
        ClientApp.setUsername(username);
    }

    public static WindowController getActiveController() {
        return ClientApp.getActiveController();
    }

    public static void setActiveController(WindowController controller) {
        ClientApp.setActiveController(controller);
    }

    public static WindowController getSecondaryController() { return ClientApp.getSecondaryController();}

    public static void setSecondaryController(WindowController controller) {ClientApp.setSecondaryController(controller);}

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

    public static FileTransferService getFileTransferService() {return FileTransferService.getFiletransferService();}

    public static CommandDictionaryService getCommandDictionary() {
        return new ClientCommandDictionaryService();
    }

    public static ViewFilesInClientDirectory getView() {
        return new ViewFilesInClientDirectory().getView();
    }

    public static List<CommandService> getCommandServices() {
        return Arrays.asList(new SuccessLogin(),
                new ErrorCommand(),
                new InfoCommand(),
                new SuccessViewFilesOnServer());
    }
}
