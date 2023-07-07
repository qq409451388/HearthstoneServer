package com.poethan.hearthstoneclassic.combat.interfaces;

import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;

import java.util.List;

public interface IApiCombatUnitCall {
    List<AbstractCombatUnit> getCurrentCombatUnitList();
    List<AbstractCombatUnit> getOpponentUnitList();
}
