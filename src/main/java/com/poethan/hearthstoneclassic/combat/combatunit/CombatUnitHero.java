package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatAttackLog;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
import com.poethan.hearthstoneclassic.dto.ActiveCardUnit;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 英雄
 */
@Getter
@Setter
public class CombatUnitHero extends AbstractCombatUnit {
    private CombatUnitWeapon weapon;
    private CombatUnitSkill skill;
    private Integer health;
    private Integer shield;
    private Integer damage;

    public int getDamage() {
        if (Objects.isNull(weapon)) {
            return this.damage;
        }
        return this.damage + this.weapon.getCardDO().getDamage();
    }

    public void costHealth(int damage) {
        if (this.shield > 0) {
            if (damage > this.shield) {
                this.health -= (damage - this.shield);
                this.shield = 0;
            } else {
                this.shield -= damage;
            }
        } else {
            this.health -= damage;
        }
    }

    public CombatLog attack(AbstractCombatUnit abstractCombatUnit) {
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

    /**
     * 使用一个单位
     */
    @Override
    public CombatLog use(ActiveCardUnit activeCardUnit) {
        return null;
    }

}
