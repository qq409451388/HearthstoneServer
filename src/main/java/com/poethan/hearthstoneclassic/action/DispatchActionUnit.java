package com.poethan.hearthstoneclassic.action;

import com.poethan.hearthstoneclassic.dto.ChatTcpMessage;
import com.poethan.hearthstoneclassic.dto.HandShakeTcpMessage;
import com.poethan.hearthstoneclassic.dto.TcpMessage;
import com.poethan.jear.module.web.tcp.SocketHandler;
import com.poethan.jear.utils.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DispatchActionUnit extends SocketHandler<byte[]> {
    public DispatchActionUnit() {
        this.setMsgDataType(byte[].class);
    }

    @Override
    public void channelReadTrueType(ChannelHandlerContext ctx, byte[] msg) {
        String json = new String(msg).trim();
        TcpMessage tcpMessage = JsonUtils.decode(json, TcpMessage.class);
        this.dispatch(ctx, tcpMessage);
    }

    private void dispatch(ChannelHandlerContext ctx, TcpMessage tcpMessage) {
        if (tcpMessage instanceof HandShakeTcpMessage) {
            ActionUnitFactory.get(HandShakeActionUnit.class).channelReadLogic(ctx, (HandShakeTcpMessage) tcpMessage);
        } else if (tcpMessage instanceof ChatTcpMessage) {
            ActionUnitFactory.get(ChatActionUnit.class).channelReadLogic(ctx, (ChatTcpMessage) tcpMessage);
        } else {
            log.warn("Unmatched ActionUnit for message:{}", JsonUtils.encode(tcpMessage));
        }
    }
}
