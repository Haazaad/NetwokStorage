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
    public String processCommand(Command command) {
        String[] commandArgs = command.getArgs();
        setDbConnection();
        if (!tryLogin(commandArgs[0], commandArgs[1])) {
            return "Incorrect login or password.";
        }
        return "login_ok";
    }

    private boolean tryLogin(String login, String password) {
        try {
            prepareAuthRequest();
            preStatement.setString(1, login);
            preStatement.setString(2, password);
            ResultSet resultSet = preStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(1).equals("1")) return true;
            }
        } catch (SQLException e) {
            logger.throwing(Level.ERROR, e);
        } finally {
            closeDbConnection();
        }
        return false;
    }

    private void prepareAuthRequest() {
        try {
            preStatement = dbConnection.prepareStatement("select count(1)\n" +
                    "from users u\n" +
                    "left join user_auc a on a.user_id=u.user_id\n" +
                    "where u.login = ?\n" +
                    "and a.password = ?");
        } catch (SQLException e) {
            logger.throwing(Level.ERROR, e);
        }
    }

    @Override
    public String getCommand() {
        return "login";
    }
}
