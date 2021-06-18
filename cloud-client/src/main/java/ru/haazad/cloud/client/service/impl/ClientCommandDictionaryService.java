package ru.haazad.cloud.client.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.service.CommandDictionaryService;
import ru.haazad.cloud.service.CommandService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientCommandDictionaryService implements CommandDictionaryService {
    private static final Logger logger = LogManager.getLogger(ClientCommandDictionaryService.class);

    private final Map<String, CommandService> dictionaryService;

    public ClientCommandDictionaryService(){
        this.dictionaryService = Collections.unmodifiableMap(getCommandDictionary());
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
        if (dictionaryService.containsKey(command.getCommandName())) {
            return dictionaryService.get(command.getCommandName()).processCommand(command);
        }
        throw new IllegalArgumentException("The command " + command.getCommandName() + " does not exist");
    }


}
