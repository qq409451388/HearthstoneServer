package com.poethan.hearthstoneclassic.combat.combatevent;

import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;

/**
 * 对局开始
 */
public class CombatEventStartGame extends AbstractCombatEvent {
    @Override
    public void bindAction() {
    }

    @Override
    public CombatUnitActionEnum getBindAction() {
        return null;
    }

    @Override
    public void trigger(AbstractCombatUnit unit) {
        System.out.println("213213");
    }
}
