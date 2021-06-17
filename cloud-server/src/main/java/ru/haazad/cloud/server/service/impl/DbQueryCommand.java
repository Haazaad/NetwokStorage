package ru.haazad.cloud.server.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.server.factory.Factory;
import ru.haazad.cloud.server.service.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbQueryCommand {
    private static final Logger logger = LogManager.getLogger(DbQueryCommand.class);

    private DatabaseService dbService;
    protected Connection dbConnection;
    protected PreparedStatement preStatement;

    public void setDbConnection() {
        dbService = Factory.getDatabaseService();
        dbService.connect();
        dbConnection = dbService.getConnection();
    }

    public void closeDbConnection() {
        try {
            if (dbConnection != null) dbConnection.close();
        } catch (SQLException e) {
            logger.throwing(Level.ERROR, e);
        }
    }
}
