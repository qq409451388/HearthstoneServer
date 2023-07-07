package com.poethan.hearthstoneclassic.combat.ability;

import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitAttendant;
import com.poethan.hearthstoneclassic.constants.SelectorTypeConstants;
import com.poethan.hearthstoneclassic.dto.CombatUnitSelector;
import lombok.Getter;
import lombok.Setter;

/**
 * 对手全体单位造成伤害
 */
@Setter
@Getter
public class DamageAllOpponentAbility extends NumericValueAbility {
    public DamageAllOpponentAbility(CombatUnitAttendant combatUnitAttendant) {
        this.setCombatUnitAttendant(combatUnitAttendant);
        CombatUnitSelector targetSelector = new CombatUnitSelector();
        targetSelector.setSelectType(SelectorTypeConstants.SELECT_TYPE_ALL_UNIT);
        this.setTargetSelector(targetSelector);
    }

    public void trigger() {
        this.getCombatUnitAttendant().magicAttack(null);
    }
}
