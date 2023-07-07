package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatAttackLog;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.interfaces.IAbilityCombatUserUnit;
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
public class CombatUnitHero extends AbstractCombatEntityUnit {
    private CombatUnitWeapon weapon;
    private CombatUnitSkill skill;
    private Integer shield;

    public CombatUnitHero(IAbilityCombatUserUnit abilityCombatUserUnit) {
        super(abilityCombatUserUnit);
    }

    public Integer getDamage() {
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

    /**
     * 使用一个单位
     */
    @Override
    public CombatLog use(ActiveCardUnit activeCardUnit) {
        return null;
    }

    public CombatLog attack(AbstractCombatEntityUnit abstractCombatUnit) {
        CombatLog combatLog = this.attackCheck(abstractCombatUnit);
        if (Objects.nonNull(combatLog)) {
            return combatLog;
        }
        CombatAttackLog combatAttachLog = new CombatAttackLog();
        combatAttachLog.setSelfUnit(this);
        combatAttachLog.setTargetUnit(abstractCombatUnit);
        if (abstractCombatUnit instanceof CombatUnitHero) {
            combatAttachLog.setTargetCost(this.getDamage());
            combatAttachLog.setTargetPreHealth(abstractCombatUnit.getHealth());

            abstractCombatUnit.costHealth(this.getDamage());

            combatAttachLog.setTargetPostHealth((abstractCombatUnit).getHealth());
            // 设置已经攻击过
            this.setActive(false);
        } else if (abstractCombatUnit instanceof CombatUnitAttendant combatUnitAttendant) {
            combatAttachLog.setTargetCost(this.getDamage());
            combatAttachLog.setSelfPreHealth(this.getHealth());
            combatAttachLog.setTargetPreHealth(combatUnitAttendant.getHealth());

            combatUnitAttendant.costHealth(this.getDamage());
            this.costHealth(combatUnitAttendant.getDamage());

            combatAttachLog.setSelfPreHealth(this.getHealth());
            combatAttachLog.setTargetPostHealth(combatUnitAttendant.getHealth());
            // 设置已经攻击过
            this.setActive(false);
        }
        this.triggerEvent(CombatUnitActionEnum.ATTACK);

        return combatAttachLog;
    }
}
