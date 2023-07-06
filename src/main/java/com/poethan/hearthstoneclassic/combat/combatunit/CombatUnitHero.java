package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
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

    public CombatLog attack() {
        return null;
    }

    /**
     * 使用一个单位
     */
    @Override
    public CombatLog use(ActiveCardUnit activeCardUnit) {
        return null;
    }

}
