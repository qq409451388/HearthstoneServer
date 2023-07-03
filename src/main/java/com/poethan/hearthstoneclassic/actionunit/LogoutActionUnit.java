package com.poethan.hearthstoneclassic.actionunit;

import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.dto.tcpmessage.LogoutTcpMessage;
import com.poethan.hearthstoneclassic.dto.tcpmessage.TcpMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogoutActionUnit extends ActionUnit<LogoutTcpMessage> {
    @Override
    public void channelReadLogic(ChannelHandlerContext ctx, LogoutTcpMessage tcpMessage) {
        boolean res = TcpClientContainer.disconnect(tcpMessage.getSessionId(), ctx.channel());
        if (!res) {
            ActionUnit.write(ctx, TcpMessage.ERROR("forbidden."));
        }
        log.info("Client Logout, channelId{}, userName:{}, sessionId:{}", ctx.channel().id().asShortText(),
                tcpMessage.getUserName(), tcpMessage.getSessionId());
    }
}
