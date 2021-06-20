package ru.haazad.cloud.client.service.impl.command;

import ru.haazad.cloud.client.config.ConfigProperty;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ViewFilesInClientDirectory {

    private ViewFilesInClientDirectory view;

    public ViewFilesInClientDirectory() {
    }

    public ViewFilesInClientDirectory getView() {
        view = new ViewFilesInClientDirectory();
        return view;
    }

    public Path getDirectoryPath(String str) {
        str = (str == null) ? ConfigProperty.getProperties("src.directory") : str;
        Path path = Paths.get(str);
        return path.toAbsolutePath().normalize();
    }

    public List<String> getFilesInDirectory(Path path) {
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


}
