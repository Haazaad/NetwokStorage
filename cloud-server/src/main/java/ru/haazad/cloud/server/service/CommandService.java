package ru.haazad.cloud.server.service;

import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.command.CommandName;

public interface CommandService {
    Command processCommand(Command command);

    CommandName getCommand();
}
