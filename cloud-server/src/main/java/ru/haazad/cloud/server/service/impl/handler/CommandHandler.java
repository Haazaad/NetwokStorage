package ru.haazad.cloud.server.service.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.server.factory.Factory;
import ru.haazad.cloud.server.service.CommandDictionaryService;

public class CommandHandler extends SimpleChannelInboundHandler<Command> {
    private static final Logger logger = LogManager.getLogger(CommandHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command){
        logger.debug("Input command " + command.toString());
        CommandDictionaryService commandDictionary = Factory.getCommandDictionary();
        String commandResult = commandDictionary.processCommand(command);

        ctx.writeAndFlush(commandResult);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        logger.throwing(Level.ERROR, cause);
    }
}
