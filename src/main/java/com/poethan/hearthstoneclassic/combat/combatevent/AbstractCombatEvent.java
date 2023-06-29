package com.poethan.hearthstoneclassic.combat.combatevent;

import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.constants.CombatUnitAction;

abstract public class AbstractCombatEvent {
    abstract public void bindAction();
    abstract public CombatUnitAction getBindAction();
    abstract public void trigger(AbstractCombatUnit unit);
}
