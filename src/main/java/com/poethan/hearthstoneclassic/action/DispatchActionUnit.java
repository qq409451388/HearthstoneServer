package com.poethan.hearthstoneclassic.action;

import com.poethan.hearthstoneclassic.config.NoLoginException;
import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.dto.LoginNecessaryActionUnit;
import com.poethan.hearthstoneclassic.dto.TcpMessage;
import com.poethan.jear.module.web.tcp.SocketHandler;
import com.poethan.jear.utils.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

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
            if (tcpMessage instanceof LoginNecessaryActionUnit) {
                TcpClientContainer.auth(ctx.channel(), ((LoginNecessaryActionUnit) tcpMessage).getSessionId());
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
