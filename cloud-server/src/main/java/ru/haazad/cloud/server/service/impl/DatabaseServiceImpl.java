package ru.haazad.cloud.server.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.config.ConfigProperty;
import ru.haazad.cloud.server.service.DatabaseService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServiceImpl implements DatabaseService {
    private static final Logger logger = LogManager.getLogger(DatabaseServiceImpl.class);

    private static DatabaseServiceImpl databaseService;

    private Connection connection;

    private DatabaseServiceImpl() {}

    public static DatabaseServiceImpl initializeDbConnection() {
        databaseService = new DatabaseServiceImpl();
        return databaseService;
    }

    @Override
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(ConfigProperty.getProperties("db.url"), ConfigProperty.getProperties("db.user"), ConfigProperty.getProperties("db.password"));
            logger.info("Connect to database");
        } catch (ClassNotFoundException | SQLException e) {
            logger.throwing(Level.ERROR, e);
        }
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.throwing(Level.ERROR, e);
            }
        }
    }
}
