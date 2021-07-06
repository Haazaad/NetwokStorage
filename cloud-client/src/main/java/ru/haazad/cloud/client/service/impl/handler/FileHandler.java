package ru.haazad.cloud.client.service.impl.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import ru.haazad.cloud.client.service.impl.SwitchPipelineService;

import java.io.*;

@Log4j2
public class FileHandler extends ChannelInboundHandlerAdapter {
    private static String filename;
    private static String dstDirectory;
    private static long fileSize;

    public static void setFilename(String filename) {
        FileHandler.filename = filename;
    }

    public static void setDstDirectory(String dstDirectory) {
        FileHandler.dstDirectory = dstDirectory;
    }

    public static void setFileSize(long fileSize) {
        FileHandler.fileSize = fileSize;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        log.debug("Prepare upload file");
        ByteBuf byteBuf = (ByteBuf) obj;
        String dst = dstDirectory + "/" + filename;
        File file = new File(dst);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(dst, true))){
                while (byteBuf.isReadable()) {
                    out.write(byteBuf.readByte());
                }
            }
        } catch (IOException e) {
            log.throwing(Level.ERROR, e);
        }
        byteBuf.release();
        if (file.length() == fileSize) {
            SwitchPipelineService.switchAfterTransferFile(ctx);
            log.debug("Upload is finished");
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        log.throwing(cause);
    }
}
