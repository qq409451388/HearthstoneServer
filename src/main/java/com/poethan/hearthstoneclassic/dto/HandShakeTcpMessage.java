package com.poethan.hearthstoneclassic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HandShakeTcpMessage extends TcpMessage {
    private String sessionId;
    private String userName;
}
