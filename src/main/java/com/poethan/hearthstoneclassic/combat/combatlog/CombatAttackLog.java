package com.poethan.hearthstoneclassic.combat.combatlog;

import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CombatAttackLog extends CombatLog {
    private AbstractCombatUnit selfUnit;
    private AbstractCombatUnit targetUnit;
    private Integer selfPreHealth;
    private Integer selfPostHealth;
    private Integer selfCost;
    private Integer targetPreHealth;
    private Integer targetPostHealth;
    private Integer targetCost;
}