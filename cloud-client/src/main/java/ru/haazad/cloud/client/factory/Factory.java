package ru.haazad.cloud.client.factory;

import ru.haazad.cloud.client.service.AlertService;
import ru.haazad.cloud.client.service.EncryptPasswordService;
import ru.haazad.cloud.client.service.NetworkService;
import ru.haazad.cloud.client.service.impl.ClientAlertService;
import ru.haazad.cloud.client.service.impl.ClientCommandDictionaryService;
import ru.haazad.cloud.client.service.impl.EncryptPasswordServiceMD5;
import ru.haazad.cloud.client.service.impl.NettyNetworkService;
import ru.haazad.cloud.client.service.impl.command.BadLogin;
import ru.haazad.cloud.client.service.impl.command.ErrorRegistration;
import ru.haazad.cloud.client.service.impl.command.SuccessLogin;
import ru.haazad.cloud.service.CommandDictionaryService;
import ru.haazad.cloud.service.CommandService;

import java.util.Arrays;
import java.util.List;

public class Factory {

    public static NetworkService initializeNetworkService() {
        return NettyNetworkService.initializeNetwork();
    }

    public static NetworkService getNetworkService() {return NettyNetworkService.getNetwork();}

    public static EncryptPasswordService getEncryptService() {
        return EncryptPasswordServiceMD5.getEncryptService();
    }

    public static AlertService getAlertService() {
        return ClientAlertService.getAlertService();
    }

    public static CommandDictionaryService getCommandDictionary() {
        return new ClientCommandDictionaryService();
    }

    public static List<CommandService> getCommandServices() {
        return Arrays.asList(new SuccessLogin(),
                new BadLogin(),
                new ErrorRegistration());
    }
}
