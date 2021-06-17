package ru.haazad.cloud.server.service.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(MainHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        logger.info("Create new connection");
    }

}
