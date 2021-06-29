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
        String srcDirectory = (String) command.getArgs()[0];
        List<String> listFiles = getFilesInDirectory(getUserDirectory(srcDirectory));
        return new Command("ls", new Object[]{srcDirectory, listFiles});
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
        String url = ConfigProperty.getProperties("server.storage.directory") + "/" + directory;
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

    private Path getDirectoryPath(String str) {
        String url = ConfigProperty.getProperties("server.storage.directory") + "/" + str;
        Path path = Paths.get(url);
        return path.toAbsolutePath().normalize();
    }

    @Override
    public String getCommand() {
        return "ls";
    }
}
