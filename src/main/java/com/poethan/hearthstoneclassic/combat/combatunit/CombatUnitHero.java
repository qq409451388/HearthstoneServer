package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;

abstract public class CombatUnitHero extends AbstractCombatUnit {
    /**
     * 使用一个单位
     */
    @Override
    public CombatLog use() {
        return null;
    }

    public int getDamage() {
        return 0;
    }

    abstract public CombatLog attack();
}
