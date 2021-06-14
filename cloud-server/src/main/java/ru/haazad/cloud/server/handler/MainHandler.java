package ru.haazad.cloud.server.handler;

import io.netty.buffer.ByteBuf;
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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        StringBuilder sb = new StringBuilder();
        ByteBuf buf = (ByteBuf) msg;
        while (buf.readableBytes() > 0) {
            sb.append((char) buf.readByte());
        }
        logger.info(sb.toString());
        buf.release();
    }
}
