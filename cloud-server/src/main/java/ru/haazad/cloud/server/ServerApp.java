package ru.haazad.cloud.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.server.config.FlywayConfigure;
import ru.haazad.cloud.server.factory.Factory;

public class ServerApp {
    private static final Logger logger = LogManager.getLogger(ServerApp.class);

    public static void main(String[] args) {
        logger.info("Checking new flyway migration");
        FlywayConfigure.flywayMigrate();

        Factory.getServerService().startServer();
    }

}
