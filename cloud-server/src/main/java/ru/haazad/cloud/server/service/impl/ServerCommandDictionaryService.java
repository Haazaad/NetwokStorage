package ru.haazad.cloud.server.service.impl;

import ru.haazad.cloud.Command;
import ru.haazad.cloud.server.factory.Factory;
import ru.haazad.cloud.service.CommandDictionaryService;
import ru.haazad.cloud.service.CommandService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerCommandDictionaryService implements CommandDictionaryService {
    private final Map<String, CommandService> commandServiceMap;

    public ServerCommandDictionaryService() {
        this.commandServiceMap = Collections.unmodifiableMap(getCommandDictionary());
    }

    private Map<String, CommandService> getCommandDictionary() {
        List<CommandService> commandServices = Factory.getCommandServices();

        Map<String, CommandService> commandServiceMap = new HashMap<>();
        for (CommandService cs: commandServices) {
            commandServiceMap.put(cs.getCommand(), cs);
        }
        return commandServiceMap;
    }

    @Override
    public Command processCommand(Command command) {
        if (commandServiceMap.containsKey(command.getCommandName())) {
            return commandServiceMap.get(command.getCommandName()).processCommand(command);
        }
        throw new IllegalArgumentException("The command " + command.getCommandName() + " does not exist");
    }
}
