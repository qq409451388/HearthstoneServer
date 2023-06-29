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
     * 使用一个单位,对单个目标发起
     *
     * @param targetUnit 目标单位
     */
    @Override
    public void use(AbstractCombatUnit targetUnit) {
        this.triggerEvent(CombatUnitAction.E_BATTLECRY);
    }

    /**
     * 使用一个单位,对多个目标发起
     *
     * @param targetUnit 目标单位
     */
    @Override
    public void use(List<AbstractCombatUnit> targetUnit) {
        this.triggerEvent(CombatUnitAction.E_BATTLECRY);
    }

    /**
     * 死亡
     */
    public void dead() {
        this.triggerEvent(CombatUnitAction.DEAD);
    }
}
