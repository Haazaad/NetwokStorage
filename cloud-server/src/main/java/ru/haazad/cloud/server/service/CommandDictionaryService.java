package ru.haazad.cloud.server.service;

import ru.haazad.cloud.Command;

public interface CommandDictionaryService {
    String processCommand(Command command);
}
