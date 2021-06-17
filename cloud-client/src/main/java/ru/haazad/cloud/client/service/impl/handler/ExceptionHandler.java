package ru.haazad.cloud.client.service.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.client.factory.Factory;

public class ExceptionHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        Factory.getAlertService().showErrorAlert(cause);
        logger.throwing(Level.ERROR, cause);
    }
}
