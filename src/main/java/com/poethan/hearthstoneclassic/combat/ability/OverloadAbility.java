package com.poethan.hearthstoneclassic.combat.ability;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CombatEventConstants;
import com.poethan.hearthstoneclassic.constants.SelectorTypeConstants;
import com.poethan.hearthstoneclassic.dto.CombatUnitSelector;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class OverloadAbility extends NumericValueAbility {
    private Integer startRound;

    public OverloadAbility() {
        CombatUnitSelector targetCombatUnitSelector = new CombatUnitSelector();
        targetCombatUnitSelector.setSelectType(SelectorTypeConstants.SELECT_TYPE_HERO);
        this.setTargetSelector(targetCombatUnitSelector);
        this.setEvent(CombatEventConstants.E_START_OF_NEXT_COMBAT);
        this.setStartRound(this.getCombatEntityUnit().getCombatUserUnit().getRound());
    }

    @Override
    public void trigger() {
        if (!Objects.equals(this.getStartRound(), this.getCombatEntityUnit().getCombatUserUnit().getRound())) {
            return;
        }
        this.getCombatEntityUnit().getCombatUserUnit().lockMagic(this.getValue());
        CombatLog combatLog = new CombatLog();
        this.getCombatEntityUnit().getCombatUserUnit().addUndoLog(combatLog);
    }
}