package com.poethan.hearthstoneclassic.config;

import com.poethan.hearthstoneclassic.action.DispatchActionUnit;
import com.poethan.jear.module.web.tcp.SocketHandler;
import com.poethan.jear.module.web.tcp.SocketServer;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SocketServerStarter implements ApplicationRunner {
    @Resource
    private SocketServer<byte[], ByteBuf> socketServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SocketServer.SocketInitializer<byte[], ByteBuf> initializer =
                new SocketServer.SocketInitializer<>(
                    ByteArrayEncoder.class, ByteArrayDecoder.class, DispatchActionUnit.class
                );
        socketServer.setSocketInitializer(initializer);
        this.socketServer.start();
    }
}