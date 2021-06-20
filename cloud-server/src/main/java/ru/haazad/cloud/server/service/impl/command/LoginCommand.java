package ru.haazad.cloud.server.service.impl.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.server.service.CommandService;
import ru.haazad.cloud.server.service.impl.DbQueryCommand;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginCommand extends DbQueryCommand implements CommandService {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public Command processCommand(Command command) {
        if (!tryLogin((String) command.getArgs()[0], (String) command.getArgs()[1])) {
            return new Command("login_bad", new String[]{"Incorrect login or password."});
        }
        return new Command("login_ok", new Object[]{command.getArgs()[0]});
    }

    private boolean tryLogin(String login, String password) {
        try {
            setDbConnection();
            prepareIsRegister();
            preStatement.setString(1, login);
            preStatement.setString(2, password);
            ResultSet resultSet = preStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            logger.throwing(Level.ERROR, e);
        } finally {
            closeDbConnection();
        }
        return false;
    }

    @Override
    public String getCommand() {
        return "login";
    }
}
