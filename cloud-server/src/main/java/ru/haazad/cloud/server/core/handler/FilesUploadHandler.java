package ru.haazad.cloud.server.core.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.command.CommandName;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesUploadHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(FilesUploadHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        Command command = (Command) obj;
        Path dst = Paths.get((String) command.getArgs()[0]);
        ByteBuf byteBuf = (ByteBuf) command.getArgs()[1];
        logger.info("Prepare upload file");
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(dst.toString(), true))){
            Files.createFile(dst);
            while (byteBuf.isReadable()) {
                out.write(byteBuf.readByte());
            }
            byteBuf.release();
        } catch (IOException e) {
            logger.throwing(Level.ERROR, e);
        }
        ctx.writeAndFlush(new Command(CommandName.UPLOAD, new Object[]{}));
    }
}
