package com.poethan.hearthstoneclassic.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatEntityUnit;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitHero;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitSkill;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.jear.core.dto.BaseDTO;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
public class CombatUnitSelector extends BaseDTO implements ICombatUnitTargetSelector,ICombatUnitSelfSelector {
    /**
     * @see com.poethan.hearthstoneclassic.constants.SelectorTypeConstants
     */
    @NonNull
    private Integer selectType;
    /**
     * 0 self 1 opponent 2 opponent and self
     */
    @NonNull
    private Integer targetType;
    @Nullable
    private Integer handCardIndex;
    @Nullable
    private Integer combatUnitIndex;

    @JsonIgnore
    private CardDO handCard;
    @JsonIgnore
    private List<? extends AbstractCombatEntityUnit> combatUnits;
    @JsonIgnore
    private CombatUnitSkill skill;
    @JsonIgnore
    private CombatUnitHero combatUnitHero;

    public void setCombatUnits(AbstractCombatEntityUnit combatUnit) {
        this.combatUnits = List.of(combatUnit);
    }

    public void setCombatUnits(List<? extends AbstractCombatEntityUnit> combatUnit) {
        this.combatUnits = combatUnit;
    }

    @Override
    public boolean isSelf() {
        return 0 == targetType;
    }

}
