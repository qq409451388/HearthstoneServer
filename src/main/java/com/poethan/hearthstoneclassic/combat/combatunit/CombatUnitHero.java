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
    private Integer health;
    private Integer shield;
    private Integer damage;

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

}
