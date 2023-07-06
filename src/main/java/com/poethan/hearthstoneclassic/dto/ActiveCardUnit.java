package com.poethan.hearthstoneclassic.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poethan.hearthstoneclassic.combat.interfaces.IAbilityCombatUserUnit;
import com.poethan.hearthstoneclassic.constants.ActiveCardUnitConstants;
import com.poethan.jear.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * CombatUnit 动作单元
 */
@Getter
@Setter
@ToString
public class ActiveCardUnit extends BaseDTO {
    @JsonIgnore
    IAbilityCombatUserUnit combatUserUnit;
    /**
     * 选择的单位
     */
    private ICombatUnitSelfSelector selectCombatUnit;
    /**
     * 目标
     */
    private ICombatUnitTargetSelector targetCombatUnit;
    /**
     * 目标类型
     * 0 单体
     * 1 全体
     * 2 随机
     * 3 目标单位及相邻单位
     * @see ActiveCardUnitConstants
     */
    private Integer targetType;

    public ActiveCardUnit(IAbilityCombatUserUnit combatUserUnit) {
        this.combatUserUnit = combatUserUnit;
        this.targetType = ActiveCardUnitConstants.TARGET_TYPE_SINGLE;
    }
}
