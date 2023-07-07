package com.poethan.hearthstoneclassic.combat.combatunit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatAttackLog;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.interfaces.IAbilityCombatUserUnit;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
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

    public CombatLog attack(AbstractCombatUnit abstractCombatUnit) {
        if (this.combatUserUnit.hasTaunt() && !abstractCombatUnit.isTaunt()) {
            return CombatLog.Error("有嘲讽的单位必须攻击嘲讽单位");
        }
        CombatAttackLog combatAttachLog = new CombatAttackLog();
        combatAttachLog.setSelfUnit(this);
        combatAttachLog.setTargetUnit(abstractCombatUnit);
        if (abstractCombatUnit instanceof CombatUnitHero) {
            combatAttachLog.setTargetCost(this.getDamage());
            combatAttachLog.setTargetPreHealth(((CombatUnitHero)abstractCombatUnit).getHealth());
            ((CombatUnitHero)abstractCombatUnit).costHealth(this.getDamage());
            combatAttachLog.setTargetPostHealth(((CombatUnitHero)abstractCombatUnit).getHealth());
        } else if (abstractCombatUnit instanceof CombatUnitAttendant) {
            combatAttachLog.setTargetCost(this.getDamage());
            combatAttachLog.setSelfPreHealth(this.getHealth());
            combatAttachLog.setTargetPreHealth(((CombatUnitAttendant)abstractCombatUnit).getHealth());

            ((CombatUnitAttendant)abstractCombatUnit).costHealth(this.getDamage());
            this.costHealth(((CombatUnitAttendant)abstractCombatUnit).getDamage());

            combatAttachLog.setSelfPreHealth(this.getHealth());
            combatAttachLog.setTargetPostHealth(((CombatUnitAttendant)abstractCombatUnit).getHealth());
        }
        this.triggerEvent(CombatUnitActionEnum.ATTACK);
        return combatAttachLog;
    }

    abstract public void costHealth(int damage);
}
