package ru.haazad.cloud.client.service.impl.command;

import ru.haazad.cloud.Command;
import ru.haazad.cloud.service.CommandService;

public class ViewFilesInDirectoryCommand implements CommandService {
    @Override
    public Command processCommand(Command command) {
        return null;
    }

    @Override
    public String getCommand() {
        return "dir";
    }
}
