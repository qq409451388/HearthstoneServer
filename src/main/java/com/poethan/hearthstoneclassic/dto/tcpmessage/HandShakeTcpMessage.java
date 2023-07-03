package com.poethan.hearthstoneclassic.dto.tcpmessage;

import com.poethan.hearthstoneclassic.constants.ActionUnitConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HandShakeTcpMessage extends TcpMessage {
    private String sessionId;
    private String userName;
    private String deviceId;
    private String clientIp;

    public HandShakeTcpMessage() {
        this.setAction(ActionUnitConstants.UNIT_HANDSHAKE);
    }
}
