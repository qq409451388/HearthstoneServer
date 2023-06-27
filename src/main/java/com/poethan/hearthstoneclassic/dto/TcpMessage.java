package com.poethan.hearthstoneclassic.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
        @JsonSubTypes.Type(value = HandShakeTcpMessage.class, name = "HandShake"),
        @JsonSubTypes.Type(value = ChatTcpMessage.class, name = "Chat")
})
@ToString
public class TcpMessage extends BaseDTO {
    private String action;
    private Long timestamp;

    public TcpMessage() {
        this.timestamp = System.currentTimeMillis();
    }

    public byte[] toByteArray() {
        return JsonUtils.encode(this).getBytes(StandardCharsets.UTF_8);
    }
}
