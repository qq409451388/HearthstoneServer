package com.poethan.hearthstoneclassic.dto;

import com.poethan.hearthstoneclassic.constants.UserConstants;
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
    private Integer status;

    public UserActiveData(String sessionId) {
        this.sessionId = sessionId;
        this.status = UserConstants.ACTIVE_FREE;
    }

    /**
     * 是否空闲
     */
    public boolean isFree() {
        return UserConstants.ACTIVE_FREE == status;
    }
}
