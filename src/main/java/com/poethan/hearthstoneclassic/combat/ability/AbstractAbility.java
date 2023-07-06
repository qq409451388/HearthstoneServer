package com.poethan.hearthstoneclassic.combat.ability;

import com.poethan.hearthstoneclassic.combat.combatevent.AbstractCombatEvent;
import com.poethan.jear.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class AbstractAbility extends BaseDTO {
    private String action;

    abstract public AbstractCombatEvent getEvent();
}
