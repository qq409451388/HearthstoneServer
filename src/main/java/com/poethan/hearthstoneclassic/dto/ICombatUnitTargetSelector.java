package com.poethan.hearthstoneclassic.dto;

import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatEntityUnit;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitHero;

import java.util.List;

public interface ICombatUnitTargetSelector {
    Integer getSelectType();
    Integer getCombatUnitIndex();
    List<? extends AbstractCombatEntityUnit> getCombatUnits();
    void setCombatUnits(AbstractCombatEntityUnit combatUnit);
    void setCombatUnits(List<? extends AbstractCombatEntityUnit> combatUnit);
    void setCombatUnitHero(CombatUnitHero combatUnitHero);

    /**
     * 目标是否是己方
     */
    boolean isSelf();
}