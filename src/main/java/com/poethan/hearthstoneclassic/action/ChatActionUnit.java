package com.poethan.hearthstoneclassic.action;

import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.constants.ActionUnitConstants;
import com.poethan.hearthstoneclassic.dto.ChatTcpMessage;
import com.poethan.hearthstoneclassic.dto.TcpMessage;
import com.poethan.hearthstoneclassic.dto.UserSession;
import com.poethan.hearthstoneclassic.logic.RedisLogic;
import com.poethan.jear.utils.SystemUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ChatActionUnit extends ActionUnit<ChatTcpMessage> {
    @Resource
    private RedisLogic redisLogic;

    @Override
    public void channelReadLogic(ChannelHandlerContext ctx, ChatTcpMessage tcpMessage) {
        String sendTo = tcpMessage.getSendTo();
        UserSession userSession = redisLogic.getUserSession(sendTo);
        Channel channelSendTo = TcpClientContainer.getClient(userSession.getSessionId());

        // 消息回复
        ChatTcpMessage replyMessage = new ChatTcpMessage();
        replyMessage.setContent(tcpMessage.getContent());
        replyMessage.setFrom(tcpMessage.getFrom());
        replyMessage.setSendTo(tcpMessage.getSendTo());
        boolean sendRes = ActionUnit.write(channelSendTo, replyMessage);

        // 通知发起方
        if (sendRes) {
            ActionUnit.write(ctx, TcpMessage.OK());
        } else {
            ActionUnit.write(ctx, TcpMessage.ERROR(tcpMessage.getSendTo()+" No Login."));
        }
    }
}
