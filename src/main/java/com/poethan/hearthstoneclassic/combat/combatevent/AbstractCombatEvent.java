package com.poethan.hearthstoneclassic.combat.combatevent;

import com.poethan.hearthstoneclassic.combat.ability.AbstractAbility;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
import org.springframework.context.ApplicationEvent;

abstract public class AbstractCombatEvent {
    abstract public void trigger(AbstractCombatUnit unit);
}
