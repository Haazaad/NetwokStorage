package ru.haazad.cloud.server.service.impl.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.server.service.CommandService;
import ru.haazad.cloud.server.service.impl.DbQueryCommand;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterCommand extends DbQueryCommand implements CommandService {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public Command processCommand(Command command) {
        setDbConnection();
        if (!checkRegister((String) command.getArgs()[0], (String) command.getArgs()[1])) {
            logger.trace("Register new user invoked");
            try {
                preStatement = dbConnection.prepareStatement("select registerNewUser(?, ?, ?);");
                for (int i = 0; i < command.getArgs().length; i++) {
                    preStatement.setString(i + 1, (String) command.getArgs()[i]);
                }
                logger.trace("Try call " + preStatement.toString());
                ResultSet result = preStatement.executeQuery();
                while (result.next()) {
                    if (result.getBoolean(1)) {
                        return new Command("register_ok", null);
                    }
                }
            } catch (SQLException e) {
                logger.throwing(Level.ERROR, e);
            }
        }
        logger.trace("Registration new user is failed");
        return new Command("register_bad", new Object[]{"Register new user is failed"});
    }

    private boolean checkRegister(String login, String password) {
        try {
            prepareIsRegister();
            logger.trace("checkRegister for " + login + " invoked");
            preStatement.setString(1, login);
            preStatement.setString(2, password);
            ResultSet result = preStatement.executeQuery();
            while (result.next()) {
                return result.getBoolean(1);
            }
        } catch (SQLException e) {
            logger.throwing(Level.ERROR, e);
        }
        return false;
    }

    @Override
    public String getCommand() {
        return "register";
    }
}
