package com.poethan.hearthstoneclassic.dto;

import com.poethan.hearthstoneclassic.constants.ActionUnitConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatTcpMessage extends LoginNecessaryActionUnit {
    private String content;
    private String sendTo;
    private String from;

    public ChatTcpMessage() {
        this.setAction(ActionUnitConstants.UNIT_CHAT);
    }
}
