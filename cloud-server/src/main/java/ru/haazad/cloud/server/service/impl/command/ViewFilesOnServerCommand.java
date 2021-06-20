package ru.haazad.cloud.server.service.impl.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
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
    private static Logger logger = LogManager.getLogger(ViewFilesOnServerCommand.class);

    @Override
    public Command processCommand(Command command) {
        List<String> listFiles;
        String login = (String) command.getArgs()[0];
        Path srcDirectory = getUserDirectory(login);
        String directory = (command.getArgs().length > 1) ? (String) command.getArgs()[1] : null;
        if (directory != null) {
            String directoryPath = login + "/" + directory;
            Path path = getDirectoryPath(directoryPath);
            listFiles = getFilesInDirectory(path);
        } else {
            listFiles = getFilesInDirectory(srcDirectory);        }

        return new Command("ls_ok", new Object[]{srcDirectory, listFiles});
    }

    private List<String> getFilesInDirectory(Path path) {
        List<String> list = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path p: stream) {
                list.add(p.getFileName().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Path getUserDirectory(String login) {
        String url = ConfigProperty.getProperties("server.storage.directory") + "/" + login;
        Path path = Paths.get(url);
        if (!Files.exists(path)){
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                logger.throwing(Level.ERROR, e);
            }
        }
        return path;
    }

    private Path getDirectoryPath(String str) {
        String url = ConfigProperty.getProperties("server.storage.directory" ) + "/" + str;
        Path path = Paths.get(url);
        return path.toAbsolutePath().normalize();
    }

    @Override
    public String getCommand() {
        return "ls";
    }
}
