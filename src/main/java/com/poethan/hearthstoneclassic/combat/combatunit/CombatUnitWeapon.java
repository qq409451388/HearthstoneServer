package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CombatUnitConstants;

public class CombatUnitWeapon extends AbstractCombatUnit{
    public CombatUnitWeapon() {
        this.setCombatUnitType(CombatUnitConstants.TYPE_WEAPON);
    }

    /**
     * 使用一个单位
     */
    @Override
    public CombatLog use() {
        return null;
    }
}
