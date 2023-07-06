package com.poethan.hearthstoneclassic.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitHero;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitSkill;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.jear.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CombatUnitSelector extends BaseDTO implements ICombatUnitTargetSelector,ICombatUnitSelfSelector {
    /**
     * @see com.poethan.hearthstoneclassic.constants.SelectorTypeConstants
     */
    private Integer selectType;
    /**
     * 0 self 1 opponent
     */
    private Integer targetType;
    private Integer handCardIndex;
    private Integer combatUnitIndex;
    @JsonIgnore
    private CardDO handCard;
    @JsonIgnore
    private List<AbstractCombatUnit> combatUnits;
    @JsonIgnore
    private CombatUnitSkill skill;
    @JsonIgnore
    private CombatUnitHero combatUnitHero;

    public void setCombatUnits(AbstractCombatUnit combatUnit) {
        this.combatUnits = List.of(combatUnit);
    }

    public void setCombatUnits(List<AbstractCombatUnit> combatUnit) {
        this.combatUnits = combatUnit;
    }

    @Override
    public boolean isSelf() {
        return 0 == targetType;
    }
}
