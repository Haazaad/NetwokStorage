package ru.haazad.cloud.server.core.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.log4j.Log4j2;
import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.command.CommandName;
import ru.haazad.cloud.server.core.handler.CommandHandler;
import ru.haazad.cloud.server.core.handler.FileHandler;

@Log4j2
public class SwitchPipelineService {

    public static void switchToFileUpload(ChannelHandlerContext context) {
        context.pipeline().addLast(new ChunkedWriteHandler(), new FileHandler());
        context.pipeline().remove(ObjectEncoder.class);
        context.pipeline().remove(ObjectDecoder.class);
        context.pipeline().remove(CommandHandler.class);
        log.debug("Switch pipeline to upload");
    }

    public static void switchAfterUpload(ChannelHandlerContext context, String dstDirectory) {
        context.pipeline().remove(ChunkedWriteHandler.class);
        context.pipeline().remove(FileHandler.class);
        context.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                new ObjectEncoder(),
                new CommandHandler());
        Command cmd = new Command(CommandName.UPLOAD_SUCCESS, new Object[]{"Upload is finished", dstDirectory});
        context.writeAndFlush(cmd);
        log.debug("Sending command" + cmd);
        log.debug("Switch pipeline to command");
    }

    public static void switchToFileDownload(ChannelHandlerContext context) {
        context.pipeline().addLast(new ChunkedWriteHandler());
        context.pipeline().remove(ObjectEncoder.class);
    }

    public static void switchAfterDownload(ChannelHandlerContext context) {
        context.pipeline().addLast(new ObjectEncoder());
        context.pipeline().remove(ChunkedWriteHandler.class);
    }
}
