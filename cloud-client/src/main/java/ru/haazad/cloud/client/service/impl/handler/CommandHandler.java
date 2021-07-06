package ru.haazad.cloud.client.service.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.client.service.impl.SwitchPipelineService;
import ru.haazad.cloud.command.Command;
import ru.haazad.cloud.client.factory.Factory;
import ru.haazad.cloud.client.service.CommandDictionaryService;
import ru.haazad.cloud.command.CommandName;
import ru.haazad.cloud.command.FileInfo;

public class CommandHandler extends SimpleChannelInboundHandler<Command> {
    private static final Logger logger = LogManager.getLogger(CommandHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command) {
        logger.debug("Input command " + command.toString());
        if (command.getCommandName() == CommandName.READY) {
            if (command.haveImportantArgs(2)) {
                SwitchPipelineService.switchToTransferFile(ctx);
                FileInfo path = (FileInfo) command.getArgs()[0];
                Factory.getNetworkService().sendFile(path.getPath());
            }
            return;
        }
        CommandDictionaryService commandDictionary = Factory.getCommandDictionary();
        commandDictionary.processCommand(command);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.throwing(Level.ERROR, cause);
        Factory.getAlertService().showErrorAlert(cause.toString());
    }
}
