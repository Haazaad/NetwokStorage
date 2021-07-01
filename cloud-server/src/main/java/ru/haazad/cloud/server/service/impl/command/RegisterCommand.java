package ru.haazad.cloud.server.service.impl.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.command.CommandName;
import ru.haazad.cloud.server.factory.Factory;
import ru.haazad.cloud.server.service.CommandService;

public class RegisterCommand implements CommandService {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public Command processCommand(Command command) {
        if (!command.haveImportantArgs(3)) {
            return new Command(CommandName.LOGIN_ERROR, new Object[]{"Not enough parameters for register"});
        }
        if (!Factory.getDatabaseService().checkLogin(command.getArgs())) {
            if (Factory.getDatabaseService().tryRegister(command.getArgs())) {
                return new Command(CommandName.REGISTER_SUCCESS, null);
            }
        }
        logger.trace("Registration new user is failed");
        return new Command(CommandName.REGISTER_SUCCESS, new Object[]{"Register new user is failed"});
    }

    @Override
    public CommandName getCommand() {
        return CommandName.REGISTER;
    }
}
