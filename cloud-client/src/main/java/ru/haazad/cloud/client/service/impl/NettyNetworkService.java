package ru.haazad.cloud.client.service.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.client.service.NetworkService;
import ru.haazad.cloud.config.ConfigProperty;

public class NettyNetworkService implements NetworkService {
    private static final Logger logger = LogManager.getLogger(NettyNetworkService.class);

    private static NettyNetworkService network;
    private static SocketChannel channel;

    private NettyNetworkService() {
    }

    public static NettyNetworkService getNetwork() {
        network = new NettyNetworkService();
        initializeNetworkService();
        return network;
    }

    private static void initializeNetworkService() {
        new Thread(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
                            @Override
                            protected void initChannel(io.netty.channel.socket.SocketChannel socketChannel) {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(new StringDecoder(),
                                        new StringEncoder());
                            }
                        });
                ChannelFuture future = b.connect(ConfigProperty.getProperties("server.host"), Integer.parseInt(ConfigProperty.getProperties("server.port"))).sync();
                logger.info("Connecting to server " + ConfigProperty.getProperties("server.host") + " on port " + ConfigProperty.getProperties("server.port"));
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                logger.throwing(Level.ERROR, e);
            } finally {
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    @Override
    public void sendCommand(String command) {
        channel.writeAndFlush(command);
    }

    @Override
    public String readCommandResult() {
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            channel.close().sync();
        } catch (InterruptedException e) {
            logger.throwing(Level.ERROR, e);
        }
    }
}
