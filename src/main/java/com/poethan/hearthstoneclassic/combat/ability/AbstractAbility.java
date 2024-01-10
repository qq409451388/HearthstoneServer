package com.poethan.hearthstoneclassic.combat.ability;

import com.poethan.hearthstoneclassic.combat.CombatScene;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatEntityUnit;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
import com.poethan.hearthstoneclassic.dto.ICombatUnitSelfSelector;
import com.poethan.hearthstoneclassic.dto.ICombatUnitTargetSelector;
import com.poethan.jear.core.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class AbstractAbility extends BaseDTO {
    private AbstractCombatEntityUnit combatEntityUnit;
    /**
     * 主动方选择器，可以为null，默认为当前单位
     */
    private ICombatUnitSelfSelector selfSelector;

    /**
     * 目标选择器
     */
    private ICombatUnitTargetSelector targetSelector;

    /**
     * 触发事件绑定
     * @see CombatUnitActionEnum
     */
    private String event;

    abstract public void trigger();

}