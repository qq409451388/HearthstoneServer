package com.poethan.hearthstoneclassic.combat.combatunit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.function.AbstractFunc;
import com.poethan.hearthstoneclassic.combat.interfaces.IAbilityCombatUserUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class AbstractCombatEntityUnit extends AbstractCombatUnit {
    protected Integer health;
    protected Integer damage;

    @JsonIgnore
    private IAbilityCombatUserUnit combatUserUnit;

    public AbstractCombatEntityUnit(IAbilityCombatUserUnit abilityCombatUserUnit) {
        this.combatUserUnit = abilityCombatUserUnit;
    }

    abstract public CombatLog attack(AbstractCombatEntityUnit combatEntityUnit);

    protected CombatLog attackCheck(AbstractCombatEntityUnit abstractCombatUnit) {
        if (this.getCombatUserUnit().hasValidTauntCombatUnits()
                && (abstractCombatUnit instanceof CombatUnitHero
                || (
                abstractCombatUnit instanceof CombatUnitAttendant
                        && !((CombatUnitAttendant) abstractCombatUnit).hasTaunt()
        )
        )
        ) {
            return CombatLog.Error("有嘲讽的单位必须攻击嘲讽单位");
        }
        if (abstractCombatUnit instanceof CombatUnitAttendant) {
            if (((CombatUnitAttendant) abstractCombatUnit).hasStealth()) {
                return CombatLog.Error("潜行单位不能被攻击");
            }
        }
        if (!this.getAllowTargetType().contains(abstractCombatUnit.getClass())) {
            return CombatLog.Error("目标单位不可选中");
        }
        return null;
    }

    abstract public void costHealth(int damage);

    public void applyFunc(AbstractFunc func) {
        func.loadUnit(this).apply();
    }
}
