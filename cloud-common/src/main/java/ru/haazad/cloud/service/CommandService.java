package ru.haazad.cloud.service;

import ru.haazad.cloud.Command;

public interface CommandService {
    Command processCommand(Command command);

    String getCommand();
}
