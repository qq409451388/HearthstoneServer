package com.poethan.hearthstoneclassic.dto;

import com.poethan.jear.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserActiveData extends BaseDTO {
    private String userName;
    private String sessionId;

    public UserActiveData(String sessionId) {
        this.sessionId = sessionId;
    }
}
