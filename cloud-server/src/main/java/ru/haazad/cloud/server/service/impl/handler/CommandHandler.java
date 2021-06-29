package ru.haazad.cloud.server.service.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.server.factory.Factory;
import ru.haazad.cloud.server.service.CommandDictionaryService;
import ru.haazad.cloud.server.service.impl.command.FilesUploadHandler;

public class CommandHandler extends SimpleChannelInboundHandler<Command> {
    private static final Logger logger = LogManager.getLogger(CommandHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command){
        if (command.getCommandName().equals("upload")) {
            ctx.pipeline().addLast(new ChunkedWriteHandler());
            ctx.pipeline().addLast(new FilesUploadHandler());
            ctx.pipeline().remove(ObjectDecoder.class);
        }
        logger.debug("Input command " + command.toString());
        CommandDictionaryService commandDictionary = Factory.getCommandDictionary();
        Command commandResult = commandDictionary.processCommand(command);
        logger.debug("Result is " + commandResult.toString());

        ctx.writeAndFlush(commandResult);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        logger.throwing(Level.ERROR, cause);
    }
}
