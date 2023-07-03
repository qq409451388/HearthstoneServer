package com.poethan.hearthstoneclassic.dto.tcpmessage;

import com.poethan.hearthstoneclassic.constants.ActionUnitConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogoutTcpMessage extends LoginNecessaryActionUnit {
    private String userName;

    public LogoutTcpMessage() {
        this.setAction(ActionUnitConstants.UNIT_LOGOUT);
    }
}
