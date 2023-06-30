package com.poethan.hearthstoneclassic.combat;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitHero;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitSkill;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitWeapon;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 英雄
 */
@Getter
@Setter
public class Career extends CombatUnitHero {
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
}
