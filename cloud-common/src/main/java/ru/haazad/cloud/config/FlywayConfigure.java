package ru.haazad.cloud.config;

import org.flywaydb.core.Flyway;

public class FlywayConfigure {

    public static void flywayMigrate(){
        Flyway flyway = Flyway.configure().dataSource(ConfigProperty.getProperties("db.url"), ConfigProperty.getProperties("db.user"), ConfigProperty.getProperties("db.password")).load();
        flyway.migrate();
    }
}
