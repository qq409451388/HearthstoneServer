package com.poethan.hearthstoneclassic.action;

import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.dto.ChatTcpMessage;
import com.poethan.hearthstoneclassic.dto.TcpMessage;
import com.poethan.hearthstoneclassic.dto.UserSession;
import com.poethan.hearthstoneclassic.logic.RedisLogic;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ChatActionUnit extends ActionUnit<ChatTcpMessage> {
    @Resource
    private RedisLogic redisLogic;

    @Override
    public Class<ChatActionUnit> getSelfClass() {
        return ChatActionUnit.class;
    }

    @Override
    public void channelReadLogic(ChannelHandlerContext ctx, ChatTcpMessage tcpMessage) {
        String sendTo = tcpMessage.getSendTo();
        UserSession userSession = redisLogic.getUserSession(sendTo);
        Channel chatToChannel = TcpClientContainer.getClient(userSession.getSessionId());
        TcpMessage replyMessage = new TcpMessage();
        replyMessage.setAction("REPLY");
        chatToChannel.writeAndFlush(replyMessage.toByteArray());
    }
}
