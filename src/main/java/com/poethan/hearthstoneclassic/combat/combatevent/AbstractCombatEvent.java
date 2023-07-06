package com.poethan.hearthstoneclassic.combat.combatevent;

import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;

abstract public class AbstractCombatEvent {
    abstract public void bindAction(String action);
    abstract public CombatUnitActionEnum getBindAction();
    abstract public void trigger(AbstractCombatUnit unit);
}
