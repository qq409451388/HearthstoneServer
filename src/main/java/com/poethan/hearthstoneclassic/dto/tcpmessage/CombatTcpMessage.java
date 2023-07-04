package com.poethan.hearthstoneclassic.dto.tcpmessage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poethan.hearthstoneclassic.combat.CombatSceneUserUnit;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.ActionUnitConstants;
import com.poethan.hearthstoneclassic.constants.CombatEventConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CombatTcpMessage extends LoginNecessaryTcpMessage {
    private String gameId;
    private Integer method;
    @JsonIgnore
    private List<Long> cardIds;
    private CombatLog combatLog;
    private CombatSceneUserUnit combatSceneUserUnit;

    public static CombatTcpMessage initCombat(CombatSceneUserUnit combatSceneUserUnit) {
        CombatTcpMessage combatTcpMessage = new CombatTcpMessage();
        combatTcpMessage.setAction(ActionUnitConstants.UNIT_COMBAT);
        combatTcpMessage.combatSceneUserUnit = combatSceneUserUnit;
        return combatTcpMessage;
    }

    public static CombatTcpMessage firstRound(CombatSceneUserUnit combatSceneUserUnit) {
        CombatTcpMessage combatTcpMessage = new CombatTcpMessage();
        combatTcpMessage.setAction(CombatEventConstants.E_START_OF_GAME);
        combatTcpMessage.combatSceneUserUnit = combatSceneUserUnit;
        return combatTcpMessage;
    }
}
