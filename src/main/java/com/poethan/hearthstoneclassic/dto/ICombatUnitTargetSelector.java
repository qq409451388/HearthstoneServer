package com.poethan.hearthstoneclassic.dto;

import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitHero;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;

import java.util.List;

public interface ICombatUnitTargetSelector {
    int getSelectType();
    Integer getCombatUnitIndex();
    void setCombatUnits(AbstractCombatUnit combatUnit);
    void setCombatUnits(List<AbstractCombatUnit> combatUnit);
    void setCareer(CombatUnitHero combatUnitHero);

    /**
     * 目标是否是己方
     */
    boolean isSelf();
}