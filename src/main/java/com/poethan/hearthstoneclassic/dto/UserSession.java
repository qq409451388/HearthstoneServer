package com.poethan.hearthstoneclassic.dto;

import com.poethan.jear.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSession extends BaseDTO {
    private String sessionId;
    private String userName;
}
