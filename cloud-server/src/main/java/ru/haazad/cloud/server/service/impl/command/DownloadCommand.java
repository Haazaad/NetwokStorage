package ru.haazad.cloud.server.service.impl.command;

import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.command.CommandName;
import ru.haazad.cloud.command.FileInfo;
import ru.haazad.cloud.server.config.ConfigProperty;
import ru.haazad.cloud.server.service.CommandService;

import java.nio.file.Paths;

public class DownloadCommand implements CommandService {
    @Override
    public Command processCommand(Command command) {
       if (command.haveImportantArgs(2)) {
           String srcPath = ConfigProperty.getStorage() + "\\" + (String) command.getArgs()[0];
           FileInfo info = new FileInfo(Paths.get(srcPath));
           return new Command(CommandName.PREPARE_DOWNLOAD, new Object[]{info, command.getArgs()[1]});
       }
       return new Command(CommandName.ERROR, new Object[]{"Not enough parameters for download file"});
    }

    @Override
    public CommandName getCommand() {
        return CommandName.DOWNLOAD;
    }
}
