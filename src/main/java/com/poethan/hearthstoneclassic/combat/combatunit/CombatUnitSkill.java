package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.jear.core.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CombatUnitSkill extends BaseDTO {
    private CardDO cardDO;
    private Integer cost;
}
