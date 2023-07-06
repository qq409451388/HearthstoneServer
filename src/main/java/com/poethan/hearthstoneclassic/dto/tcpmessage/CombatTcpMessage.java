package com.poethan.hearthstoneclassic.dto.tcpmessage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poethan.hearthstoneclassic.combat.CombatSceneUserUnit;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.interfaces.IAbilityCombatUserUnit;
import com.poethan.hearthstoneclassic.constants.ActionUnitConstants;
import com.poethan.hearthstoneclassic.constants.CombatConstants;
import com.poethan.hearthstoneclassic.constants.CombatEventConstants;
import com.poethan.hearthstoneclassic.dto.ActiveCardUnit;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CombatTcpMessage extends LoginNecessaryTcpMessage {
    private String gameId;
    /**
     * @see com.poethan.hearthstoneclassic.constants.CombatConstants
     */
    private Integer method;
    @JsonIgnore
    private List<Long> cardIds;
    private List<CombatLog> combatLog;
    private IAbilityCombatUserUnit combatSceneUserUnit;
    private ActiveCardUnit activeCardUnit;

    public CombatTcpMessage() {

    }

    public CombatTcpMessage(String gameId) {
        this.setAction(ActionUnitConstants.UNIT_COMBAT);
        this.setGameId(gameId);
    }

    public static CombatTcpMessage initCombat(String gameId, IAbilityCombatUserUnit combatSceneUserUnit) {
        CombatTcpMessage combatTcpMessage = new CombatTcpMessage(gameId);
        combatTcpMessage.combatSceneUserUnit = combatSceneUserUnit;
        return combatTcpMessage;
    }

    public static CombatTcpMessage firstRound(String gameId, CombatSceneUserUnit combatSceneUserUnit) {
        CombatTcpMessage combatTcpMessage = new CombatTcpMessage(gameId);
        combatTcpMessage.setMethod(CombatConstants.COMBAT_START_GAME);
        combatTcpMessage.combatSceneUserUnit = combatSceneUserUnit;
        return combatTcpMessage;
    }

    public static CombatTcpMessage select(String gameId, ActiveCardUnit activeCardUnit) {
        CombatTcpMessage combatTcpMessage = new CombatTcpMessage(gameId);
        combatTcpMessage.setAction(ActionUnitConstants.UNIT_COMBAT);
        combatTcpMessage.setMethod(CombatConstants.COMBAT_SELECT);
        combatTcpMessage.activeCardUnit = activeCardUnit;
        return combatTcpMessage;
    }

    public static CombatTcpMessage drop(String gameId) {
        CombatTcpMessage combatTcpMessage = new CombatTcpMessage(gameId);
        combatTcpMessage.setAction(ActionUnitConstants.UNIT_COMBAT);
        combatTcpMessage.setMethod(CombatConstants.COMBAT_DROP);
        return combatTcpMessage;
    }
}
