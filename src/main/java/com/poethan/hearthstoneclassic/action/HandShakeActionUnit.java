package com.poethan.hearthstoneclassic.action;

import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.dto.HandShakeTcpMessage;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class HandShakeActionUnit extends ActionUnit<HandShakeTcpMessage> {

    @Override
    public Class<HandShakeActionUnit> getSelfClass() {
        return HandShakeActionUnit.class;
    }

    @Override
    public void channelReadLogic(ChannelHandlerContext ctx, HandShakeTcpMessage tcpMessage) {
        TcpClientContainer.addClient(tcpMessage.getSessionId(), ctx.channel());
    }
}
