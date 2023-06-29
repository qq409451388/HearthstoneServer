package com.poethan.hearthstoneclassic.action;

import com.poethan.hearthstoneclassic.dto.TcpMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.PostConstruct;

import java.util.Objects;

abstract public class ActionUnit<M extends TcpMessage> {
    @PostConstruct
    public void addToFactory() {
        ActionUnitFactory.set(this.getClass().getSimpleName().replace("ActionUnit", ""), this);
    }

    abstract public void channelReadLogic(ChannelHandlerContext ctx, M tcpMessage);

    public static void write(ChannelHandlerContext ctx, TcpMessage tcpMessage) {
        ctx.writeAndFlush(tcpMessage.toByteArray());
    }

    public static boolean write(Channel channel, TcpMessage tcpMessage) {
        if (Objects.nonNull(channel) && channel.isActive()) {
            channel.writeAndFlush(tcpMessage.toByteArray());
            return true;
        }
        return false;
    }
}
