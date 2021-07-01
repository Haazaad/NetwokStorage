package ru.haazad.cloud.server.service;

import ru.haazad.cloud.command.Command;

@FunctionalInterface
public interface CommandDictionaryService {
    Command processCommand(Command command);
}
