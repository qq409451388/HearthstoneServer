package com.poethan.hearthstoneclassic.combat.combatlog;

import com.poethan.jear.core.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CombatLog extends BaseDTO {
    private String gameId;
    private String logId;
    private String preLogId;
    private String fromUser;
    private String toUser;
    private String action;
    private String msg;

    public static CombatLog Error(String msg) {
        CombatLog combatLog = new CombatLog();
        combatLog.setAction("error");
        combatLog.setMsg(msg);
        return combatLog;
    }
}
