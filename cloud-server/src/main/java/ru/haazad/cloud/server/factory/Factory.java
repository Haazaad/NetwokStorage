package ru.haazad.cloud.server.factory;

import ru.haazad.cloud.server.service.CommandDictionaryService;
import ru.haazad.cloud.server.service.CommandService;
import ru.haazad.cloud.server.service.DatabaseService;
import ru.haazad.cloud.server.service.ServerService;
import ru.haazad.cloud.server.service.impl.CommandDictionaryServiceImpl;
import ru.haazad.cloud.server.service.impl.PostgreDatabaseService;
import ru.haazad.cloud.server.service.impl.NettyServerService;
import ru.haazad.cloud.server.service.impl.command.LoginCommand;

import java.util.Arrays;
import java.util.List;

public class Factory {

    public static ServerService getServerService() {
        return NettyServerService.initializeServerService();
    }

    public static DatabaseService getDatabaseService() {
        return PostgreDatabaseService.initializeDbConnection();
    }

    public static CommandDictionaryService getCommandDictionary() {
        return new CommandDictionaryServiceImpl();
    }

    public static List<CommandService> getCommandServices() {
        return Arrays.asList(new LoginCommand());
    }
}
