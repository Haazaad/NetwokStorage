package ru.haazad.cloud.server.core.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.command.CommandName;
import ru.haazad.cloud.command.FileInfo;
import ru.haazad.cloud.server.core.impl.SwitchPipelineService;
import ru.haazad.cloud.server.factory.Factory;
import ru.haazad.cloud.server.service.CommandDictionaryService;

public class CommandHandler extends SimpleChannelInboundHandler<Command> {
    private static final Logger logger = LogManager.getLogger(CommandHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command){
        logger.debug("Input command " + command.toString());
        if (command.getCommandName() == CommandName.PREPARE_UPLOAD) {
            FileInfo info = (FileInfo) command.getArgs()[0];
            FileHandler.setFilename(info.getFileName());
            FileHandler.setDstDirectory((String) command.getArgs()[1]);
            FileHandler.setFileSize(info.getSize());
            ctx.writeAndFlush(new Command(CommandName.READY, command.getArgs()));
            SwitchPipelineService.switchToFileUpload(ctx);
        } else {
            CommandDictionaryService commandDictionary = Factory.getCommandDictionary();
            Command commandResult = commandDictionary.processCommand(command);
            logger.debug("Result is " + commandResult.toString());
            ctx.writeAndFlush(commandResult);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        logger.throwing(Level.ERROR, cause);
    }
}
