package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.constants.CombatUnitAction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 随从
 */
@Getter
@Setter
@ToString
public class CombatUnitAttendant extends AbstractCombatUnit {

    /**
     * 随从上场
     */
    @Override
    public void use() {
        this.triggerEvent(CombatUnitAction.E_BATTLECRY);
    }

    /**
     * 死亡
     */
    public void dead() {
        this.triggerEvent(CombatUnitAction.DEAD);
    }

    public void attack() {
        this.triggerEvent(CombatUnitAction.ATTACK);
    }
}
