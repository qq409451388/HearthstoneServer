package com.poethan.hearthstoneclassic.dto.tcpmessage;

import com.poethan.hearthstoneclassic.combat.CombatSceneUserUnit;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CombatEventConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CombatTcpMessage extends TcpMessage {
    private CombatLog combatLog;
    private CombatSceneUserUnit combatSceneUserUnit;

    public static CombatTcpMessage firstRound(CombatSceneUserUnit combatSceneUserUnit) {
        CombatTcpMessage combatTcpMessage = new CombatTcpMessage();
        combatTcpMessage.setAction(CombatEventConstants.E_START_OF_GAME);
        combatTcpMessage.combatSceneUserUnit = combatSceneUserUnit;
        return combatTcpMessage;
    }
}
