package com.poethan.hearthstoneclassic.dto.tcpmessage;

import com.poethan.hearthstoneclassic.constants.ActionUnitConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombatSearchTcpMessage extends LoginNecessaryActionUnit {
    private Long deckId;
    private String toUserName;
    private String gameId;
    /**
     * 0: 搜索对手
     * 1: 取消搜索
     * 2: 挑战
     * 3: 拒绝
     * 4: 接受
     * 5: 取消
     * 6: 战斗中
     * 7: 战斗结束
     */
    private Integer method;
    public CombatSearchTcpMessage() {
        this.setAction(ActionUnitConstants.UNIT_COMBAT_SEARCH);
    }
}
