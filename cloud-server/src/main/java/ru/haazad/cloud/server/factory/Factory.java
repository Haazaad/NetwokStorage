package ru.haazad.cloud.server.factory;

import ru.haazad.cloud.server.service.DatabaseService;
import ru.haazad.cloud.server.service.ServerService;
import ru.haazad.cloud.server.service.impl.DatabaseServiceImpl;
import ru.haazad.cloud.server.service.impl.NettyServerService;

public class Factory {

    public static ServerService getServerService() {
        return NettyServerService.initializeServerService();
    }

    public static DatabaseService getDatabaseService() {
        return DatabaseServiceImpl.initializeDbConnection();
    }
}
