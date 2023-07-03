package com.poethan.hearthstoneclassic.dto.tcpmessage;

import com.poethan.hearthstoneclassic.dto.tcpmessage.TcpMessage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
abstract public class LoginNecessaryActionUnit extends TcpMessage {
    private String sessionId;
    private String userName;
}
