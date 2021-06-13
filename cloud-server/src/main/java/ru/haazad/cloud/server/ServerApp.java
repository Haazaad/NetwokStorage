package ru.haazad.cloud.server;

import ru.haazad.cloud.config.FlywayConfigure;
import ru.haazad.cloud.server.factory.Factory;

public class ServerApp {
    public static void main(String[] args) {
        FlywayConfigure.flywayMigrate();

        Factory.getServerService().startServer();
    }
}
