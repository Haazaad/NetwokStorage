package ru.haazad.cloud.client.service;

import ru.haazad.cloud.Command;

public interface CommandService {
    void processCommand(Command command);

    String getCommand();
}
