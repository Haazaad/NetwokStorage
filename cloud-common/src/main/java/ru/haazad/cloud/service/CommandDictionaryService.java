package ru.haazad.cloud.service;

import ru.haazad.cloud.Command;

public interface CommandDictionaryService {
    Command processCommand(Command command);
}
