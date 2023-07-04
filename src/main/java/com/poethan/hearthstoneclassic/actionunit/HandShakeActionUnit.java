package com.poethan.hearthstoneclassic.actionunit;

import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.dto.tcpmessage.HandShakeTcpMessage;
import com.poethan.hearthstoneclassic.dto.tcpmessage.TcpMessage;
import com.poethan.hearthstoneclassic.dto.UserSession;
import com.poethan.hearthstoneclassic.logic.RedisLogic;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class HandShakeActionUnit extends ActionUnit<HandShakeTcpMessage> {
    @Resource
    private RedisLogic redisLogic;

    @Override
    public void channelReadLogic(ChannelHandlerContext ctx, HandShakeTcpMessage tcpMessage) {
        UserSession userSession = redisLogic.getUserSession(tcpMessage.getUserName());
        if (Objects.isNull(userSession)) {
            ActionUnit.write(ctx, TcpMessage.ERROR());
            return;
        }
        if (!userSession.getSessionId().equals(tcpMessage.getSessionId())) {
            ActionUnit.write(ctx, TcpMessage.ERROR());
            return;
        }
        Channel client = TcpClientContainer.getClient(tcpMessage.getSessionId());
        if (Objects.nonNull(client) && client.isActive()) {
            if (client.equals(ctx.channel())) {
                TcpClientContainer.addClient(tcpMessage.getSessionId(), ctx.channel(), true);
                ActionUnit.write(client, TcpMessage.ERROR(" Your account had logged."));
            } else {
                ActionUnit.write(client, TcpMessage.ALERT(" Your account is being logged in on another device."));
                ActionUnit.write(ctx, TcpMessage.ERROR());
            }
        } else if (Objects.nonNull(client) && !client.isActive()) {
            this.reHandShake(ctx, tcpMessage);
        } else {
            TcpClientContainer.addClient(tcpMessage.getSessionId(), ctx.channel(), false);
            TcpClientContainer.getActiveData(tcpMessage.getSessionId()).setUserName(tcpMessage.getUserName());
            ActionUnit.write(ctx, TcpMessage.OK());
        }
    }

    /**
     * 因异常退出，重新握手
     */
    private void reHandShake(ChannelHandlerContext ctx, HandShakeTcpMessage tcpMessage) {
        TcpClientContainer.disconnectException(tcpMessage.getSessionId());

        // 通知客户端
        TcpClientContainer.addClient(tcpMessage.getSessionId(), ctx.channel(), true);
        ActionUnit.write(ctx, TcpMessage.OK());
    }
}
