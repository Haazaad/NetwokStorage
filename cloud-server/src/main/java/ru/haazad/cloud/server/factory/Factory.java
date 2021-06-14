package ru.haazad.cloud.server.factory;

import ru.haazad.cloud.server.service.DatabaseService;
import ru.haazad.cloud.server.service.ServerService;
import ru.haazad.cloud.server.service.impl.PostgreDatabaseService;
import ru.haazad.cloud.server.service.impl.NettyServerService;

public class Factory {

    public static ServerService getServerService() {
        return NettyServerService.initializeServerService();
    }

    public static DatabaseService getDatabaseService() {
        return PostgreDatabaseService.initializeDbConnection();
    }
}
