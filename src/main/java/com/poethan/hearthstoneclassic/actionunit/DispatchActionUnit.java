package com.poethan.hearthstoneclassic.actionunit;

import com.poethan.hearthstoneclassic.config.NoLoginException;
import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.dto.tcpmessage.LoginNecessaryTcpMessage;
import com.poethan.hearthstoneclassic.dto.tcpmessage.TcpMessage;
import com.poethan.hearthstoneclassic.logic.RedisLogic;
import com.poethan.jear.utils.JsonUtils;
import com.poethan.jear.web.tcp.SocketHandler;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class DispatchActionUnit extends SocketHandler<byte[]> {
    @Resource
    private RedisLogic redisLogic;

    public DispatchActionUnit() {
        this.setMsgDataType(byte[].class);
    }

    @Override
    public void channelReadTrueType(ChannelHandlerContext ctx, byte[] msg) {
        String json = new String(msg).trim();
        TcpMessage tcpMessage = JsonUtils.decode(json, TcpMessage.class);
        this.dispatch(ctx, tcpMessage);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        try {
            super.handlerRemoved(ctx);
            TcpClientContainer.remove(ctx.channel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatch(ChannelHandlerContext ctx, TcpMessage tcpMessage) {
        try {
            ActionUnit au = ActionUnitFactory.get(tcpMessage.getAction());
            if (Objects.isNull(au)) {
                log.warn("Unmatched ActionUnit for message:{}", JsonUtils.encode(tcpMessage));
                return;
            }
            if (tcpMessage instanceof LoginNecessaryTcpMessage) {
                TcpClientContainer.auth(ctx.channel(), ((LoginNecessaryTcpMessage) tcpMessage).getSessionId());
            }
            au.channelReadLogic(ctx, tcpMessage);
        } catch (NoLoginException nle) {
            ActionUnit.write(ctx, TcpMessage.ERROR("No HandShake."));
        } catch (Exception e) {
            log.error(e.getMessage());
            ActionUnit.write(ctx, TcpMessage.ERROR(e.getMessage()));
        }
    }
}
