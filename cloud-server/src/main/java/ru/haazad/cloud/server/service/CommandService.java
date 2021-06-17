package ru.haazad.cloud.server.service;

import ru.haazad.cloud.Command;

public interface CommandService {
    String processCommand(Command command);

    String getCommand();
}
