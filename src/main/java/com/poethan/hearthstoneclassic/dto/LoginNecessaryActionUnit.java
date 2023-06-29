package com.poethan.hearthstoneclassic.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
abstract public class LoginNecessaryActionUnit extends TcpMessage {
    private String sessionId;
}
