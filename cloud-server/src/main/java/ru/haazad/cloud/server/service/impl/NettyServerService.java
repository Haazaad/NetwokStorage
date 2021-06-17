package ru.haazad.cloud.server.service.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.config.ConfigProperty;
import ru.haazad.cloud.server.service.ServerService;
import ru.haazad.cloud.server.service.impl.handler.CommandHandler;
import ru.haazad.cloud.server.service.impl.handler.MainHandler;

public class NettyServerService implements ServerService {
    private static final Logger logger = LogManager.getLogger(NettyServerService.class);

    private static NettyServerService serverService;

    private NettyServerService(){}

    public static NettyServerService initializeServerService() {
        serverService = new NettyServerService();
        return serverService;
    }

    @Override
    public void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new CommandHandler(),
                                    new MainHandler());
                        }
                    });
            ChannelFuture future = b.bind(Integer.parseInt(ConfigProperty.getProperties("server.port"))).sync();
            logger.info("Server is running on port: " + ConfigProperty.getProperties("server.port"));
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.throwing(Level.ERROR, e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
