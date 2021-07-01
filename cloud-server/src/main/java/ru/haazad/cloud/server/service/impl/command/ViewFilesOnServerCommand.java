package ru.haazad.cloud.server.service.impl.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.command.CommandName;
import ru.haazad.cloud.server.config.ConfigProperty;
import ru.haazad.cloud.server.service.CommandService;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ViewFilesOnServerCommand implements CommandService {
    private static final Logger logger = LogManager.getLogger(ViewFilesOnServerCommand.class);

    @Override
    public Command processCommand(Command command) {
        String srcDirectory = (String) command.getArgs()[0];
        List<String> listFiles = getFilesInDirectory(getUserDirectory(srcDirectory));
        return new Command(CommandName.LS, new Object[]{srcDirectory, listFiles});
    }

    private List<String> getFilesInDirectory(Path path) {
        List<String> list = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path p : stream) {
                list.add(p.getFileName().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Path getUserDirectory(String directory) {
        String url = ConfigProperty.getStorage() + "/" + directory;
        Path path = Paths.get(url);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                logger.throwing(Level.ERROR, e);
            }
        }
        return path;
    }

    @Override
    public CommandName getCommand() {
        return CommandName.LS;
    }
}
