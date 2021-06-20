package ru.haazad.cloud.client.service.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.Command;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.CommandDictionaryService;

public class CommandHandler extends SimpleChannelInboundHandler<Command> {
    private static final Logger logger = LogManager.getLogger(CommandHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command){
        logger.debug("Input command " + command.toString());
        CommandDictionaryService commandDictionary = Factory.getCommandDictionary();
        commandDictionary.processCommand(command);
        ctx.channel().pipeline().addLast(new ObjectEncoder(),
                new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                new CommandHandler());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        logger.throwing(Level.ERROR, cause);
    }
}
