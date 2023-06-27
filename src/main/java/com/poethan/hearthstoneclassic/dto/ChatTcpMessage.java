package com.poethan.hearthstoneclassic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatTcpMessage extends TcpMessage {
    private String content;
    private String sendTo;
    private String from;
}
