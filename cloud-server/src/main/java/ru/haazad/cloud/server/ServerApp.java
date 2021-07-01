package ru.haazad.cloud.server;

import ru.haazad.cloud.server.config.FlywayConfig;
import ru.haazad.cloud.server.factory.Factory;

public class ServerApp {

    public static void main(String[] args) {
        FlywayConfig.flywayMigrate();

        Factory.getServerService().startServer();
    }

}
