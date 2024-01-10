package com.poethan.hearthstoneclassic.dto;

import com.poethan.hearthstoneclassic.constants.UserConstants;
import com.poethan.jear.core.dto.BaseDTO;
import com.poethan.jear.utils.EzDataUtils;
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
    private String gameId;

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

    /**
     * 是否进入了游戏界面
     */
    public boolean isInGame() {
        return UserConstants.ACTIVE_IN_COMBAT == status && EzDataUtils.check(this.gameId);
    }
}
