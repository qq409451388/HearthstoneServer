package com.poethan.hearthstoneclassic.dto.tcpmessage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
abstract public class LoginNecessaryTcpMessage extends TcpMessage {
    private String sessionId;
    private String userName;
}
