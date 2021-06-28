package ru.haazad.cloud.client.service;

import ru.haazad.cloud.Command;

public interface CommandDictionaryService {
    void processCommand(Command command);
}
