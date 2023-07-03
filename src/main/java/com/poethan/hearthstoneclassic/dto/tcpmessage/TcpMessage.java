package com.poethan.hearthstoneclassic.dto.tcpmessage;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.poethan.hearthstoneclassic.constants.ActionUnitConstants;
import com.poethan.jear.dto.BaseDTO;
import com.poethan.jear.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "action",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HandShakeTcpMessage.class, name = ActionUnitConstants.UNIT_HANDSHAKE),
        @JsonSubTypes.Type(value = ChatTcpMessage.class, name = ActionUnitConstants.UNIT_CHAT),
        @JsonSubTypes.Type(value = LogoutTcpMessage.class, name = ActionUnitConstants.UNIT_LOGOUT),
        @JsonSubTypes.Type(value = CombatSearchTcpMessage.class, name = ActionUnitConstants.UNIT_COMBAT_SEARCH),
        @JsonSubTypes.Type(value = CombatTcpMessage.class, name = ActionUnitConstants.UNIT_COMBAT)
})
@ToString
public class TcpMessage extends BaseDTO {
    private String action;
    private Long timestamp;
    private String msg;

    public TcpMessage() {
        this.timestamp = System.currentTimeMillis();
    }

    public byte[] toByteArray() {
        return JsonUtils.encode(this).getBytes(StandardCharsets.UTF_8);
    }

    public static TcpMessage OK() {
        TcpMessage message = new TcpMessage();
        message.setAction(ActionUnitConstants.UNIT_ACK);
        return message;
    }

    public static TcpMessage ERROR() {
        return ERROR("ERROR");
    }

    public static TcpMessage ERROR(String msg) {
        TcpMessage message = new TcpMessage();
        message.setAction(ActionUnitConstants.UNIT_ERROR);
        message.setMsg(msg);
        return message;
    }

    public static TcpMessage ALERT(String msg) {
        TcpMessage message = new TcpMessage();
        message.setAction(ActionUnitConstants.UNIT_ALERT);
        message.setMsg(msg);
        return message;
    }
}
