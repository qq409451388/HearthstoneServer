package com.poethan.hearthstoneclassic.actionunit;

import com.poethan.hearthstoneclassic.dto.tcpmessage.CombatTcpMessage;
import io.netty.channel.ChannelHandlerContext;

public class CombatActionUnit extends ActionUnit<CombatTcpMessage> {

    @Override
    public void channelReadLogic(ChannelHandlerContext ctx, CombatTcpMessage tcpMessage) {

    }
}
