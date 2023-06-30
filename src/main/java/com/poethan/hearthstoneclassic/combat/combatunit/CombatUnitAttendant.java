package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
import com.poethan.hearthstoneclassic.constants.CombatUnitConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 随从
 */
@Getter
@Setter
@ToString
public class CombatUnitAttendant extends AbstractCombatUnit {
    private boolean canAttack;

    public CombatUnitAttendant() {
        this.setCombatUnitType(CombatUnitConstants.TYPE_ATTENDANT);
        this.canAttack = false;
    }

    /**
     * 随从上场
     */
    @Override
    public CombatLog use() {
        this.triggerEvent(CombatUnitActionEnum.BATTLECRY);
        return null;
    }

    /**
     * 死亡
     */
    public CombatLog dead() {
        this.triggerEvent(CombatUnitActionEnum.DEAD);
        return null;
    }

    /**
     * 攻击
     */
    public CombatLog attack() {
        this.triggerEvent(CombatUnitActionEnum.ATTACK);
        return null;
    }
}
