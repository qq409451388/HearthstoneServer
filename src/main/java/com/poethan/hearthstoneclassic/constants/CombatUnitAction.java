package com.poethan.hearthstoneclassic.constants;

import com.poethan.hearthstoneclassic.combat.combatevent.*;
import lombok.Getter;

@Getter
public enum CombatUnitAction {
    START_OF_GAME(CombatEventConstants.E_START_OF_GAME, CombatEventConstants.E_START_OF_GAME_DESC, CombatEventStartGame.class),
    START_OF_COMBAT(CombatEventConstants.E_START_OF_COMBAT, CombatEventConstants.E_START_OF_COMBAT_DESC, CombatEventStartCombat.class),
    E_CASTSWHENDRAWN(CombatEventConstants.E_CASTSWHENDRAWN, CombatEventConstants.E_CASTSWHENDRAWN_DESC, CombatEventCastsWhenDrawn.class),
    E_BATTLECRY(CombatEventConstants.E_BATTLECRY, CombatEventConstants.E_BATTLECRY_DESC, CombatEventBattlecry.class),
    E_COMBO(CombatEventConstants.E_COMBO, CombatEventConstants.E_COMBO_DESC, CombatEventCombo.class),
    E_ATTACK(CombatEventConstants.E_ATTACK, CombatEventConstants.E_ATTACK_DESC, CombatEventAttack.class),
    DEAD(CombatEventConstants.E_DEATHRATTLE, CombatEventConstants.E_DEATHRATTLE_DESC, CombatEventDead.class);

    private String type;
    private String typeDesc;
    private Class<? extends AbstractCombatEvent> action;

    CombatUnitAction(String type, String desc, Class<? extends AbstractCombatEvent> action) {
        this.type = type;
        this.typeDesc = desc;
        this.action = action;
    }
}
