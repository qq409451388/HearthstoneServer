package com.poethan.hearthstoneclassic.action;

import com.poethan.hearthstoneclassic.dto.TcpMessage;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.PostConstruct;

abstract public class ActionUnit<M extends TcpMessage> {
    @PostConstruct
    public void addToFactory() {
        ActionUnitFactory.set(getSelfClass(), this);
    }

    abstract public Class<? extends ActionUnit<M>> getSelfClass();

    abstract public void channelReadLogic(ChannelHandlerContext ctx, M tcpMessage);
}
