package ru.haazad.cloud.client.service;

import io.netty.handler.stream.ChunkedFile;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.command.CommandName;
import ru.haazad.cloud.client.factory.Factory;

import java.io.File;
import java.io.IOException;

public class FileTransferService {
    private static final Logger logger = LogManager.getLogger(FileTransferService.class);

    private NetworkService networkService;
    private static FileTransferService filetransfer;

    private FileTransferService() {}

    public static FileTransferService getFiletransferService() {
        filetransfer = new FileTransferService();
        return filetransfer;
    }

    public void sendFile(String srcPath, String dstPath) {
        networkService = Factory.getNetworkService();
        try {
            ChunkedFile srcFile = new ChunkedFile(new File(srcPath));
            Command cmd = new Command(CommandName.UPLOAD, new Object[]{dstPath, srcFile});
            logger.info(String.format("Prepare to upload file %s", srcPath));
            networkService.sendCommand(cmd);
        } catch (IOException e) {
            logger.throwing(Level.ERROR, e);
        }

    }
}
