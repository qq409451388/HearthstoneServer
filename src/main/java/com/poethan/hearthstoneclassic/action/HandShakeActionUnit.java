package com.poethan.hearthstoneclassic.action;

import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.dto.HandShakeTcpMessage;
import com.poethan.hearthstoneclassic.dto.TcpMessage;
import com.poethan.hearthstoneclassic.dto.UserSession;
import com.poethan.hearthstoneclassic.logic.RedisLogic;
import com.poethan.jear.module.cache.EzRedis;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class HandShakeActionUnit extends ActionUnit<HandShakeTcpMessage> {
    @Resource
    private RedisLogic redisLogic;

    @Override
    public Class<HandShakeActionUnit> getSelfClass() {
        return HandShakeActionUnit.class;
    }

    @Override
    public void channelReadLogic(ChannelHandlerContext ctx, HandShakeTcpMessage tcpMessage) {
        UserSession userSession = redisLogic.getUserSession(tcpMessage.getUserName());
        if (Objects.isNull(userSession)) {
            ctx.channel().writeAndFlush(TcpMessage.ERROR().toByteArray());
            return;
        }
        if (userSession.getSessionId().equals(tcpMessage.getSessionId())) {
            ctx.channel().writeAndFlush(TcpMessage.ERROR().toByteArray());
            return;
        }
        TcpClientContainer.addClient(tcpMessage.getSessionId(), ctx.channel());
        ctx.channel().writeAndFlush(TcpMessage.OK().toByteArray());
    }
}
