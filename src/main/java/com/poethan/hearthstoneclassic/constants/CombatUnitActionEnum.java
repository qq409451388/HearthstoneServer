package com.poethan.hearthstoneclassic.constants;

import com.poethan.hearthstoneclassic.combat.combatevent.*;
import lombok.Getter;

@Getter
public enum CombatUnitActionEnum {
    START_OF_GAME(CombatEventConstants.E_START_OF_GAME, CombatEventConstants.E_START_OF_GAME_DESC, CombatEventStartGame.class),
    START_OF_COMBAT(CombatEventConstants.E_START_OF_COMBAT, CombatEventConstants.E_START_OF_COMBAT_DESC, CombatEventStartRound.class),
    START_OF_NEXT_COMBAT(CombatEventConstants.E_START_OF_NEXT_COMBAT, CombatEventConstants.E_START_OF_NEXT_COMBAT_DESC, CombatEventNextRoundStart.class),
    CASTSWHENDRAWN(CombatEventConstants.E_CASTSWHENDRAWN, CombatEventConstants.E_CASTSWHENDRAWN_DESC, CombatEventCastsWhenDrawn.class),
    BATTLECRY(CombatEventConstants.E_BATTLECRY, CombatEventConstants.E_BATTLECRY_DESC, CombatEventBattlecry.class),
    COMBO(CombatEventConstants.E_COMBO, CombatEventConstants.E_COMBO_DESC, CombatEventCombo.class),
    ATTACK(CombatEventConstants.E_ATTACK, CombatEventConstants.E_ATTACK_DESC, CombatEventAttack.class),
    DEAD(CombatEventConstants.E_DEATHRATTLE, CombatEventConstants.E_DEATHRATTLE_DESC, CombatEventDead.class);

    private String type;
    private String typeDesc;
    private Class<? extends AbstractCombatEvent> action;

    CombatUnitActionEnum(String type, String desc, Class<? extends AbstractCombatEvent> action) {
        this.type = type;
        this.typeDesc = desc;
        this.action = action;
    }
}
