package ru.haazad.cloud.server.service;

import ru.haazad.cloud.Command;

public interface CommandService {
    Command processCommand(Command command);

    String getCommand();
}
